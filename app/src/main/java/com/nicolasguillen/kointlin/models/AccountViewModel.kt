package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

class AccountViewModel(private val apiRepository: ApiRepository,
                       private val walletRepository: WalletRepository): AccountViewModelInputs, AccountViewModelOutputs{

    //INPUTS
    private val viewDidLoad = PublishProcessor.create<Unit>()
    private val insert = PublishProcessor.create<Unit>()
    private val remove = PublishProcessor.create<Unit>()

    //OUTPUTS
    private val totalAmount = PublishProcessor.create<Double>()
    override fun totalAmount(): Flowable<Double> = totalAmount

    val inputs: AccountViewModelInputs = this
    val outputs: AccountViewModelOutputs = this

    init {
        viewDidLoad
                .switchMap { walletRepository.getAllAssets() }
                .subscribe {
                    totalAmount.onNext(it.fold(0.0, { acc, asset -> acc + asset.amount }))
                }

        insert.subscribe { walletRepository.insertAsset(Asset("BTC", "Bitcoin", 5000.0)) }

        remove
                .switchMap { walletRepository.getAssetByShortName("BTC") }
                .subscribe { walletRepository.deleteAsset(it) }
    }

    override fun viewDidLoad() = viewDidLoad.onNext(Unit)
    override fun didPressInsert() = insert.onNext(Unit)
    override fun didPressRemove() = remove.onNext(Unit)
}

interface AccountViewModelInputs {
    fun viewDidLoad()
    fun didPressInsert()
    fun didPressRemove()
}

interface AccountViewModelOutputs {
    fun totalAmount(): Flowable<Double>
}
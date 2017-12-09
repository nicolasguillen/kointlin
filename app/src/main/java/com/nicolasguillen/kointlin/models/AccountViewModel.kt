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
    }

    override fun viewDidLoad() = viewDidLoad.onNext(Unit)
}

interface AccountViewModelInputs {
    fun viewDidLoad()
}

interface AccountViewModelOutputs {
    fun totalAmount(): Flowable<Double>
}
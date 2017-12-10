package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.withLatestFrom

class NewAssetViewModel(private val walletRepository: WalletRepository): NewAssetViewModelInputs, NewAssetViewModelOutputs{

    //INPUTS
    private val viewDidLoad = PublishProcessor.create<Unit>()
    private val amount = PublishProcessor.create<String>()
    private val save = PublishProcessor.create<Unit>()

    //OUTPUTS
    private val didSave = PublishProcessor.create<Unit>()
    override fun didSave(): Flowable<Unit> = didSave

    val inputs: NewAssetViewModelInputs = this
    val outputs: NewAssetViewModelOutputs = this

    init {

        save
                .withLatestFrom(amount) { _, x -> NewAssetData(x) }
                .map { Asset("BTC", "Bitcoin", it.amount.toDouble()) }
                .doOnNext { walletRepository.insertAsset(it) }
                .subscribe { didSave.onNext(Unit) }
    }

    override fun viewDidLoad() = this.viewDidLoad.onNext(Unit)
    override fun didEnterAmount(amount: String) = this.amount.onNext(amount)
    override fun didPressSave() = this.save.onNext(Unit)

    class NewAssetData(val amount: String)
}

interface NewAssetViewModelInputs {
    fun viewDidLoad()
    fun didEnterAmount(amount: String)
    fun didPressSave()
}

interface NewAssetViewModelOutputs {
    fun didSave(): Flowable<Unit>
}
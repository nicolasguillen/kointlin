package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.reponses.CoinPreview
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.withLatestFrom

class NewAssetViewModel(private val apiRepository: ApiRepository,
                        private val walletRepository: WalletRepository): NewAssetViewModelInputs, NewAssetViewModelOutputs{

    //INPUTS
    private val viewDidLoad = PublishProcessor.create<Unit>()
    private val allCoins = PublishProcessor.create<List<CoinPreview>>()
    private val coinSelected = PublishProcessor.create<String>()
    private val amount = PublishProcessor.create<String>()
    private val save = PublishProcessor.create<Unit>()

    //OUTPUTS
    private val allSupportedCoins = PublishProcessor.create<List<String>>()
    override fun allSupportedCoins(): Flowable<List<String>> = allSupportedCoins
    private val didSave = PublishProcessor.create<Unit>()
    override fun didSave(): Flowable<Unit> = didSave
    private val apiError = PublishProcessor.create<Unit>()
    override fun apiError(): Flowable<Unit> = apiError

    val inputs: NewAssetViewModelInputs = this
    val outputs: NewAssetViewModelOutputs = this

    init {

        viewDidLoad
                .switchMap { apiRepository.getSupportedCoins() }
                .subscribe(
                        { allCoins.onNext(it) },
                        { apiError.onNext(Unit) }
                )

        allCoins
                .map { it.mapNotNull { it.name }.sorted() }
                .subscribe { allSupportedCoins.onNext(it) }

        save
                .withLatestFrom(coinSelected, amount) { _, x, y -> NewAssetData(x, y) }
                .map { Asset(it.coin, it.coin, it.amount.toDouble()) }
                .doOnNext { walletRepository.insertAsset(it) }
                .subscribe { didSave.onNext(Unit) }

    }

    override fun viewDidLoad() = this.viewDidLoad.onNext(Unit)
    override fun didChooseCoin(coin: String) = this.coinSelected.onNext(coin)
    override fun didEnterAmount(amount: String) = this.amount.onNext(amount)
    override fun didPressSave() = this.save.onNext(Unit)

    class NewAssetData(val coin: String, val amount: String)
}

interface NewAssetViewModelInputs {
    fun viewDidLoad()
    fun didChooseCoin(coin: String)
    fun didEnterAmount(amount: String)
    fun didPressSave()
}

interface NewAssetViewModelOutputs {
    fun allSupportedCoins(): Flowable<List<String>>
    fun apiError(): Flowable<Unit>
    fun didSave(): Flowable<Unit>
}
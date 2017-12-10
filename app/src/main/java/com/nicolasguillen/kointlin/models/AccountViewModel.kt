package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.*
import io.reactivex.processors.PublishProcessor

class AccountViewModel(private val apiRepository: ApiRepository,
                       private val walletRepository: WalletRepository): AccountViewModelInputs, AccountViewModelOutputs{

    //INPUTS
    private val viewDidLoad = PublishProcessor.create<Unit>()
    private val didPressAdd = PublishProcessor.create<Unit>()

    //OUTPUTS
    private val isLoading = PublishProcessor.create<Boolean>()
    override fun isLoading(): Flowable<Boolean> = isLoading
    private val assets = PublishProcessor.create<List<Asset>>()
    override fun assets(): Flowable<List<Asset>> = assets
    private val totalAmount = PublishProcessor.create<Double>()
    override fun totalAmount(): Flowable<Double> = totalAmount
    private val startNewAssetActivity = PublishProcessor.create<Unit>()
    override fun startNewAssetActivity(): Flowable<Unit> = startNewAssetActivity

    val inputs: AccountViewModelInputs = this
    val outputs: AccountViewModelOutputs = this

    init {
        viewDidLoad
                .switchMap { walletRepository.getAllAssets() }
                .subscribe { assets.onNext(it) }

        assets
                .switchMap { this.calculateValueFromWallet(it) }
                .doOnNext { isLoading.onNext(false) }
                .subscribe { totalAmount.onNext(it) }

        didPressAdd.subscribe(startNewAssetActivity)
    }

    override fun viewDidLoad() = viewDidLoad.onNext(Unit)
    override fun didPressAdd() = didPressAdd.onNext(Unit)

    private fun calculateValueFromWallet(assetList: List<Asset>): Flowable<Double>{

        if(assetList.isEmpty()){
            return Flowable.just(0.0)
        } else {
            return Flowable.create({ observer ->

                Flowable.fromIterable(assetList)
                        .switchMap { asset ->
                            this.apiRepository.getPriceDetailFromCoin(asset.shortName)
                                    .doOnSubscribe { isLoading.onNext(true) }
                                .map { it.lastPriceList.first() }
                                .map { it.lastLocaleString.toDouble() * asset.amount }
                        }
                        .toList()
                        .map { it.sum() }
                        .subscribe { sum -> observer.onNext(sum) }

            }, BackpressureStrategy.LATEST)
        }
    }
}

interface AccountViewModelInputs {
    fun viewDidLoad()
    fun didPressAdd()
}

interface AccountViewModelOutputs {
    fun isLoading(): Flowable<Boolean>
    fun assets(): Flowable<List<Asset>>
    fun totalAmount(): Flowable<Double>
    fun startNewAssetActivity(): Flowable<Unit>
}
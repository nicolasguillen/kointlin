package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.usecases.FetchMyAssetsResult
import com.nicolasguillen.kointlin.usecases.GetPriceResult
import com.nicolasguillen.kointlin.usecases.AccountUseCase
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class AccountViewModel(private val useCase: AccountUseCase): AccountViewModelInputs, AccountViewModelOutputs{

    //INPUTS
    private val viewDidLoad = PublishSubject.create<Unit>()
    private val didPressAdd = PublishSubject.create<Unit>()

    //OUTPUTS
    private val isLoading = PublishSubject.create<Boolean>()
    override fun isLoading(): Observable<Boolean> = isLoading
    private val assets = PublishSubject.create<List<Asset>>()
    override fun assets(): Observable<List<Asset>> = assets
    private val totalAmount = PublishSubject.create<Double>()
    override fun totalAmount(): Observable<Double> = totalAmount
    private val startNewAssetActivity = PublishSubject.create<Unit>()
    override fun startNewAssetActivity(): Observable<Unit> = startNewAssetActivity

    val inputs: AccountViewModelInputs = this
    val outputs: AccountViewModelOutputs = this

    init {

        viewDidLoad
                .switchMapSingle { this.useCase.fetchAllMyAssets() }
                .subscribe { when(it){
                    is FetchMyAssetsResult.Success ->
                        assets.onNext(it.assetList)
                } }

        viewDidLoad
                .switchMapSingle {
                    this.useCase.getPriceFromAllMyAssets()
                            .doOnSubscribe { isLoading.onNext(true) }
                            .doOnSuccess { isLoading.onNext(false) }
                }
                .subscribe { when(it){
                    is GetPriceResult.Success ->
                        totalAmount.onNext(it.price)
                } }

        didPressAdd
                .subscribe(startNewAssetActivity)
    }

    override fun viewDidLoad() = viewDidLoad.onNext(Unit)
    override fun didPressAdd() = didPressAdd.onNext(Unit)

}

interface AccountViewModelInputs {
    fun viewDidLoad()
    fun didPressAdd()
}

interface AccountViewModelOutputs {
    fun isLoading(): Observable<Boolean>
    fun assets(): Observable<List<Asset>>
    fun totalAmount(): Observable<Double>
    fun startNewAssetActivity(): Observable<Unit>
}
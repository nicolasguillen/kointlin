package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.ui.views.DisplayableAsset
import com.nicolasguillen.kointlin.usecases.AccountUseCase
import com.nicolasguillen.kointlin.usecases.GetPriceResult
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class AccountViewModel(private val useCase: AccountUseCase): AccountViewModelInputs, AccountViewModelOutputs{

    //INPUTS
    private val viewDidLoad = PublishSubject.create<Unit>()
    private val didPressAdd = PublishSubject.create<Unit>()

    //OUTPUTS
    private val isLoading = PublishSubject.create<Boolean>()
    override fun isLoading(): Observable<Boolean> = isLoading
    private val displayableAssets = PublishSubject.create<List<DisplayableAsset>>()
    override fun displayableAssets(): Observable<List<DisplayableAsset>> = displayableAssets
    private val totalAmount = PublishSubject.create<Double>()
    override fun totalAmount(): Observable<Double> = totalAmount
    private val startNewAssetActivity = PublishSubject.create<Unit>()
    override fun startNewAssetActivity(): Observable<Unit> = startNewAssetActivity

    val inputs: AccountViewModelInputs = this
    val outputs: AccountViewModelOutputs = this

    init {

        viewDidLoad
                .switchMapSingle {
                    this.useCase.getDisplayableAssets()
                            .doOnSubscribe { isLoading.onNext(true) }
                            .doOnSuccess { isLoading.onNext(false) }
                }
                .subscribe { when(it){
                    is GetPriceResult.Success ->
                        displayableAssets.onNext(it.list)
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
    fun displayableAssets(): Observable<List<DisplayableAsset>>
    fun totalAmount(): Observable<Double>
    fun startNewAssetActivity(): Observable<Unit>
}
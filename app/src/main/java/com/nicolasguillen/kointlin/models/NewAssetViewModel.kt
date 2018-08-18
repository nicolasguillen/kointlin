package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.services.reponses.TopCoin
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.usecases.LoadTopAssetsResult
import com.nicolasguillen.kointlin.usecases.NewAssetUseCase
import com.nicolasguillen.kointlin.usecases.StoreAssetResult
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject

interface NewAssetViewModelInputs {
    fun viewDidLoad()
    fun didSelectCoin(coinName: String)
    fun didEnterAmount(amount: String)
    fun didPressSave()
}

interface NewAssetViewModelOutputs {
    fun allCoins(): Observable<List<TopCoin>>
    fun didSave(): Observable<Unit>
    fun isLoading(): Observable<Boolean>
    fun isFormValid(): Observable<Boolean>
}

class NewAssetViewModel(private val useCase: NewAssetUseCase): NewAssetViewModelInputs, NewAssetViewModelOutputs {

    //INPUTS
    private val viewDidLoad = PublishSubject.create<Unit>()
    private val coinName = PublishSubject.create<String>()
    private val amount = PublishSubject.create<String>()
    private val didPressSave = PublishSubject.create<Unit>()

    //OUTPUTS
    private val allCoins = PublishSubject.create<List<TopCoin>>()
    override fun allCoins(): Observable<List<TopCoin>> = allCoins
    private val didSave = PublishSubject.create<Unit>()
    override fun didSave(): Observable<Unit> = didSave
    private val isLoading = PublishSubject.create<Boolean>()
    override fun isLoading(): Observable<Boolean> = isLoading
    private val isFormValid = PublishSubject.create<Boolean>()
    override fun isFormValid(): Observable<Boolean> = isFormValid

    val inputs: NewAssetViewModelInputs = this
    val outputs: NewAssetViewModelOutputs = this

    init {
        viewDidLoad
                .switchMapSingle { this.useCase.loadAllTopCoins() }
                .subscribe { when(it){
                    is LoadTopAssetsResult.Success ->
                        allCoins.onNext(it.assetList)
                } }

        val newAssetData = PublishSubject.create<NewAssetData>()
        Observables.combineLatest(allCoins, coinName, amount) { x, y, z -> InputData(x, y, z) }
                .map { data -> NewAssetData(data.allCoins.firstOrNull { it.name == data.coinName }, data.amount) }
                .subscribe(newAssetData)

        newAssetData
                .map { it.isValid() }
                .subscribe(isFormValid)

        didPressSave
                .withLatestFrom(newAssetData) { _, data -> data.toAsset() }
                .switchMapSingle { this.useCase.storeNewAsset(it) }
                .subscribe { when(it){
                    is StoreAssetResult.Success ->
                        didSave.onNext(Unit)
                } }
    }

    override fun viewDidLoad() = this.viewDidLoad.onNext(Unit)
    override fun didSelectCoin(coinName: String) = this.coinName.onNext(coinName)
    override fun didEnterAmount(amount: String) = this.amount.onNext(amount)
    override fun didPressSave() = this.didPressSave.onNext(Unit)

    internal class InputData(val allCoins: List<TopCoin>, val coinName: String, val amount: String)
    internal class NewAssetData(private val coin: TopCoin?, private val amount: String) {
        fun isValid(): Boolean = amount.isNotEmpty() && amount.isValidNumber() && coin != null
        fun toAsset(): Asset = Asset(coin!!.id, coin.symbol, coin.name, amount.toDouble())
    }

}

fun String.isValidNumber(): Boolean {
    return try { toDouble() >= 0 }
    catch (e:Exception) { false }
}

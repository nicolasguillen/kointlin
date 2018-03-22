package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.usecases.DisplayableCurrency
import com.nicolasguillen.kointlin.usecases.FetchAvailableCurrenciesResult
import com.nicolasguillen.kointlin.usecases.SaveCurrencyResult
import com.nicolasguillen.kointlin.usecases.SetCurrencyUseCase
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject

interface SetCurrencyViewModelInputs {
    fun viewDidLoad()
    fun didSelectCurrency(displayableCurrency: DisplayableCurrency)
}

interface SetCurrencyViewModelOutputs {
    fun currencies(): Observable<List<DisplayableCurrency>>
    fun didSaveSettings(): Observable<Unit>
}

class SetCurrencyViewModel(private val useCase: SetCurrencyUseCase): SetCurrencyViewModelInputs, SetCurrencyViewModelOutputs {

    //INPUTS
    private val viewDidLoad = PublishSubject.create<Unit>()
    private val didSelectCurrency = PublishSubject.create<DisplayableCurrency>()

    //OUTPUTS
    private val currencies = PublishSubject.create<List<DisplayableCurrency>>()
    override fun currencies(): Observable<List<DisplayableCurrency>> = currencies
    private val didSaveSettings = PublishSubject.create<Unit>()
    override fun didSaveSettings(): Observable<Unit> = didSaveSettings

    val inputs: SetCurrencyViewModelInputs = this
    val outputs: SetCurrencyViewModelOutputs = this

    init {

        viewDidLoad
                .switchMapSingle { this.useCase.fetchAvailableCurrencies() }
                .subscribe { when(it) {
                    is FetchAvailableCurrenciesResult.Success ->
                        currencies.onNext(it.list)
                } }

        didSelectCurrency
                .withLatestFrom(currencies) { code, list -> list.first { it.currencyCode == code.currencyCode} }
                .switchMapSingle { this.useCase.saveCurrency(it) }
                .subscribe { when(it){
                    is SaveCurrencyResult.Success ->
                        didSaveSettings.onNext(Unit)
                } }
    }

    override fun viewDidLoad() = viewDidLoad.onNext(Unit)
    override fun didSelectCurrency(displayableCurrency: DisplayableCurrency) = didSelectCurrency.onNext(displayableCurrency)

}
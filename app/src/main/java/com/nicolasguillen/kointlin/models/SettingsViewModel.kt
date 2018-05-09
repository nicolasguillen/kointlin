package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.usecases.SettingsUseCase
import com.nicolasguillen.kointlin.usecases.SettingsUseCaseResultTypes
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface SettingsViewModelInputs {
    fun viewDidLoad()
    fun didPressSetCurrency()
}

interface SettingsViewModelOutputs {
    fun defaultCurrency(): Observable<String>
    fun appVersion(): Observable<String>
}

class SettingsViewModel(private val useCase: SettingsUseCase): SettingsViewModelInputs, SettingsViewModelOutputs {

    //INPUTS
    private val viewDidLoad = PublishSubject.create<Unit>()
    private val didPressSetCurrency = PublishSubject.create<Unit>()

    //OUTPUTS
    private val defaultCurrency = PublishSubject.create<String>()
    override fun defaultCurrency(): Observable<String> = defaultCurrency
    private val appVersion = PublishSubject.create<String>()
    override fun appVersion(): Observable<String> = appVersion

    val inputs: SettingsViewModelInputs = this
    val outputs: SettingsViewModelOutputs = this

    init {

        viewDidLoad
                .switchMapSingle { this.useCase.fetchDefaultCurrency() }
                .subscribe { when(it) {
                    is SettingsUseCaseResultTypes.FetchDefaultCurrencyResult.Success ->
                            defaultCurrency.onNext(it.defaultCurrency)
                } }

        viewDidLoad
                .switchMapSingle { this.useCase.fetchAppVersion() }
                .subscribe { when(it) {
                    is SettingsUseCaseResultTypes.FetchAppVersionResult.Success ->
                        appVersion.onNext(it.appVersion)
                } }
    }

    override fun viewDidLoad() = this.viewDidLoad.onNext(Unit)
    override fun didPressSetCurrency() = this.didPressSetCurrency.onNext(Unit)

}
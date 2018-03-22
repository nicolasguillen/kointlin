package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

interface SettingsViewModelInputs {
    fun viewDidLoad()
    fun didPressSetCurrency()
}

interface SettingsViewModelOutputs {
    fun defaultCurrency(): Observable<String>
}

class SettingsViewModel(private val appSettingsRepository: AppSettingsRepository): SettingsViewModelInputs, SettingsViewModelOutputs {

    //INPUTS
    private val viewDidLoad = PublishSubject.create<Unit>()
    private val didPressSetCurrency = PublishSubject.create<Unit>()

    //OUTPUTS
    private val defaultCurrency = PublishSubject.create<String>()
    override fun defaultCurrency(): Observable<String> = defaultCurrency

    val inputs: SettingsViewModelInputs = this
    val outputs: SettingsViewModelOutputs = this

    init {

        viewDidLoad
                .switchMapSingle { this.appSettingsRepository.getAppSettings() }
                .map { Currency.getInstance(it.currencyCode) }
                .map { "${it.displayName} ${it.currencyCode}" }
                .subscribe { defaultCurrency.onNext(it) }

    }

    override fun viewDidLoad() = this.viewDidLoad.onNext(Unit)
    override fun didPressSetCurrency() = this.didPressSetCurrency.onNext(Unit)

}
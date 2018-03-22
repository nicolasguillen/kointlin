package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import io.reactivex.Single
import java.util.*

interface SetCurrencyUseCase {
    fun fetchAvailableCurrencies(): Single<FetchAvailableCurrenciesResult>
    fun saveCurrency(displayableCurrency: DisplayableCurrency): Single<SaveCurrencyResult>
}

class SetCurrencyUseCaseImpl(private val appSettingsRepository: AppSettingsRepository): SetCurrencyUseCase {

    override fun fetchAvailableCurrencies(): Single<FetchAvailableCurrenciesResult> {
        return Single.just(listOf("USD","EUR","JPY","GBP","AUD","CAD","CHF")
                .map { DisplayableCurrency(it, Currency.getInstance(it).displayName) })
                .map { FetchAvailableCurrenciesResult.Success(it) }
    }

    override fun saveCurrency(displayableCurrency: DisplayableCurrency): Single<SaveCurrencyResult> {
        return Single.create { observer ->
            this.appSettingsRepository.getAppSettings()
                    .map { this.appSettingsRepository.saveAppSettings(
                            it.apply {
                                currencyCode = displayableCurrency.currencyCode
                            })
                    }
                    .subscribe (
                            { _ -> observer.onSuccess(SaveCurrencyResult.Success) },
                            { _ -> observer.onSuccess(SaveCurrencyResult.UnknownError) }
                    )
        }
    }
}

class DisplayableCurrency(
        val currencyCode: String,
        val displayName: String
)

sealed class FetchAvailableCurrenciesResult {
    class Success(val list: List<DisplayableCurrency>): FetchAvailableCurrenciesResult()
}

sealed class SaveCurrencyResult {
    object Success: SaveCurrencyResult()
    object UnknownError: SaveCurrencyResult()
}
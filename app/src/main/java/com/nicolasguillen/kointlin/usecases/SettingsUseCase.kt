package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.BuildConfig
import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import io.reactivex.Single
import java.util.*

interface SettingsUseCase {
    fun fetchAppVersion(): Single<SettingsUseCaseResultTypes.FetchAppVersionResult>
    fun fetchDefaultCurrency(): Single<SettingsUseCaseResultTypes.FetchDefaultCurrencyResult>
}

interface SettingsUseCaseResultTypes {
    sealed class FetchAppVersionResult {
        class Success(val appVersion: String): FetchAppVersionResult()
    }
    sealed class FetchDefaultCurrencyResult {
        class Success(val defaultCurrency: String): FetchDefaultCurrencyResult()
    }
}

class KointlinSettingsUseCase(private val appSettingsRepository: AppSettingsRepository): SettingsUseCase {

    override fun fetchAppVersion(): Single<SettingsUseCaseResultTypes.FetchAppVersionResult> {
        return Single.just(SettingsUseCaseResultTypes.FetchAppVersionResult.Success(BuildConfig.VERSION_NAME))
    }

    override fun fetchDefaultCurrency(): Single<SettingsUseCaseResultTypes.FetchDefaultCurrencyResult> {
        return Single.create { observer ->
            this.appSettingsRepository.getAppSettings()
                    .map { Currency.getInstance(it.currencyCode) }
                    .map { "${it.displayName} ${it.currencyCode}" }
                    .subscribe( { defaultCurrency ->
                        observer.onSuccess(SettingsUseCaseResultTypes.FetchDefaultCurrencyResult.Success(defaultCurrency))
                    })
        }
    }

}
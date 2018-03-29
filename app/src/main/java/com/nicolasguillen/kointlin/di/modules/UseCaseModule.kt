package com.nicolasguillen.kointlin.di.modules

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.usecases.*
import dagger.Module
import dagger.Provides

@Module
open class UseCaseModule {

    @Provides
    open fun providesLoadWalletUseCase(apiRepository: ApiRepository, walletRepository: WalletRepository, appSettingsRepository: AppSettingsRepository): AccountUseCase = AccountUseCaseImpl(apiRepository, walletRepository, appSettingsRepository)

    @Provides
    open fun providesNewAssetUseCase(apiRepository: ApiRepository, walletRepository: WalletRepository): NewAssetUseCase = NewAssetUseCaseImpl(apiRepository, walletRepository)

    @Provides
    open fun providesSetCurrencyUseCase(appSettingsRepository: AppSettingsRepository): SetCurrencyUseCase = SetCurrencyUseCaseImpl(appSettingsRepository)

    @Provides
    open fun providesLoadNewsFeedUseCase(apiRepository: ApiRepository): LoadNewsFeedUseCase = LoadNewsFeedUseCaseImpl(apiRepository)

}
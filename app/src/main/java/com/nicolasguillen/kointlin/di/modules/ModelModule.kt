package com.nicolasguillen.kointlin.di.modules

import com.nicolasguillen.kointlin.models.*
import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import com.nicolasguillen.kointlin.usecases.AccountUseCase
import com.nicolasguillen.kointlin.usecases.LoadNewsFeedUseCase
import com.nicolasguillen.kointlin.usecases.NewAssetUseCase
import com.nicolasguillen.kointlin.usecases.SetCurrencyUseCase
import dagger.Module
import dagger.Provides

@Module
open class ModelModule {

    @Provides fun providesAccountViewModel(useCase: AccountUseCase): AccountViewModel = AccountViewModel(useCase)

    @Provides fun providesNewAssetViewModel(useCase: NewAssetUseCase): NewAssetViewModel = NewAssetViewModel(useCase)

    @Provides fun providesSettingsViewModel(appSettingsRepository: AppSettingsRepository): SettingsViewModel = SettingsViewModel(appSettingsRepository)

    @Provides fun providesSetCurrencyViewModel(useCase: SetCurrencyUseCase): SetCurrencyViewModel = SetCurrencyViewModel(useCase)

    @Provides fun providesNewsFeedViewModel(useCase: LoadNewsFeedUseCase): NewsFeedViewModel = NewsFeedViewModel(useCase)

}
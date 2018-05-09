package com.nicolasguillen.kointlin.di.modules

import com.nicolasguillen.kointlin.models.*
import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import com.nicolasguillen.kointlin.usecases.*
import dagger.Module
import dagger.Provides

@Module
open class ModelModule {

    @Provides fun providesAccountViewModel(useCase: AccountUseCase): AccountViewModel = AccountViewModel(useCase)

    @Provides fun providesNewAssetViewModel(useCase: NewAssetUseCase): NewAssetViewModel = NewAssetViewModel(useCase)

    @Provides fun providesSettingsViewModel(useCase: SettingsUseCase): SettingsViewModel = SettingsViewModel(useCase)

    @Provides fun providesSetCurrencyViewModel(useCase: SetCurrencyUseCase): SetCurrencyViewModel = SetCurrencyViewModel(useCase)

    @Provides fun providesNewsFeedViewModel(useCase: LoadNewsFeedUseCase): NewsFeedViewModel = NewsFeedViewModel(useCase)

}
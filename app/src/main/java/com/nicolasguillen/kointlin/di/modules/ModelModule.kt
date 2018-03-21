package com.nicolasguillen.kointlin.di.modules

import com.nicolasguillen.kointlin.models.AccountViewModel
import com.nicolasguillen.kointlin.models.NewAssetViewModel
import com.nicolasguillen.kointlin.usecases.AccountUseCase
import com.nicolasguillen.kointlin.usecases.NewAssetUseCase
import dagger.Module
import dagger.Provides

@Module
open class ModelModule {

    @Provides fun providesAccountViewModel(useCase: AccountUseCase): AccountViewModel = AccountViewModel(useCase)

    @Provides fun providesNewAssetViewModel(useCase: NewAssetUseCase): NewAssetViewModel = NewAssetViewModel(useCase)

}
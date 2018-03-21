package com.nicolasguillen.kointlin.di.modules

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.usecases.AccountUseCase
import com.nicolasguillen.kointlin.usecases.AccountUseCaseImpl
import com.nicolasguillen.kointlin.usecases.NewAssetUseCase
import com.nicolasguillen.kointlin.usecases.NewAssetUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
open class UseCaseModule {

    @Provides
    open fun providesLoadWalletUseCase(apiRepository: ApiRepository, walletRepository: WalletRepository): AccountUseCase = AccountUseCaseImpl(apiRepository, walletRepository)

    @Provides
    open fun providesNewAssetUseCase(apiRepository: ApiRepository, walletRepository: WalletRepository): NewAssetUseCase = NewAssetUseCaseImpl(apiRepository, walletRepository)

}
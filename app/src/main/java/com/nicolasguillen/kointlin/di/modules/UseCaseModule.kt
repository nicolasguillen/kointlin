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
class UseCaseModule {

    @Provides fun providesLoadWalletUseCase(apiRepository: ApiRepository, walletRepository: WalletRepository): AccountUseCase = AccountUseCaseImpl(apiRepository, walletRepository)

    @Provides fun providesNewAssetUseCase(apiRepository: ApiRepository, walletRepository: WalletRepository): NewAssetUseCase = NewAssetUseCaseImpl(apiRepository, walletRepository)

}
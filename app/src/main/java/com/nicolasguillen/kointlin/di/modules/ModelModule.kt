package com.nicolasguillen.kointlin.di.modules

import com.nicolasguillen.kointlin.models.AccountViewModel
import com.nicolasguillen.kointlin.models.NewAssetViewModel
import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.storage.WalletRepository
import dagger.Module
import dagger.Provides

@Module
class ModelModule {

    @Provides fun providesAccountViewModel(apiRepository: ApiRepository, walletRepository: WalletRepository): AccountViewModel = AccountViewModel(apiRepository, walletRepository)

    @Provides fun providesNewAssetViewModel(walletRepository: WalletRepository): NewAssetViewModel = NewAssetViewModel(walletRepository)

}
package com.nicolasguillen.kointlin.mocks

import android.app.Application
import com.google.gson.Gson
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.di.ApplicationComponent
import com.nicolasguillen.kointlin.di.DaggerApplicationComponent
import com.nicolasguillen.kointlin.di.modules.ApplicationModule
import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.ApiService
import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import com.nicolasguillen.kointlin.storage.dao.WalletDao
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.dao.AppSettingsDao
import dagger.Module

class MockApp: KointlinApp() {

    override fun initApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(MockApplicationModule(this))
                .build()
    }

    @Module class MockApplicationModule(application: Application) : ApplicationModule(application) {

        override fun provideApiClient(apiService: ApiService,
                                      gson: Gson): ApiRepository {
            return MockApiClient()
        }

        override fun providesWalletClient(walletDao: WalletDao): WalletRepository {
            return MockWalletClient()
        }

        override fun providesAppSettingsClient(appSettingsDao: AppSettingsDao): AppSettingsRepository {
            return MockAppSettingsClient()
        }

    }


}
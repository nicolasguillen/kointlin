package com.nicolasguillen.kointlin.di

import com.nicolasguillen.kointlin.di.modules.ApplicationModule
import com.nicolasguillen.kointlin.di.modules.ModelModule
import com.nicolasguillen.kointlin.di.modules.UseCaseModule
import com.nicolasguillen.kointlin.ui.activities.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ModelModule::class, UseCaseModule::class])
interface ApplicationComponent {
    fun inject(accountActivity: AccountActivity)
    fun inject(newAssetActivity: NewAssetActivity)
    fun inject(settingsActivity: SettingsActivity)
    fun inject(setCurrencyActivity: SetCurrencyActivity)
    fun inject(newsFeedActivity: NewsFeedActivity)
}

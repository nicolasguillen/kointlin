package com.nicolasguillen.kointlin

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.nicolasguillen.kointlin.di.ApplicationComponent
import com.nicolasguillen.kointlin.di.DaggerApplicationComponent
import com.nicolasguillen.kointlin.di.modules.ApplicationModule
import com.nicolasguillen.kointlin.di.modules.ModelModule
import com.nicolasguillen.kointlin.di.modules.UseCaseModule
import io.fabric.sdk.android.Fabric

open class KointlinApp : Application() {

    companion object {
        @JvmStatic lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = initApplicationComponent()

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
    }

    open fun initApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .modelModule(ModelModule())
                .useCaseModule(UseCaseModule())
                .build()
    }

}
package com.nicolasguillen.kointlin

import android.app.Application
import com.bugsnag.android.Bugsnag
import com.nicolasguillen.kointlin.di.ApplicationComponent
import com.nicolasguillen.kointlin.di.DaggerApplicationComponent
import com.nicolasguillen.kointlin.di.modules.ApplicationModule
import com.nicolasguillen.kointlin.di.modules.ModelModule
import com.nicolasguillen.kointlin.di.modules.UseCaseModule

open class KointlinApp : Application() {

    companion object {
        @JvmStatic lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = initApplicationComponent()

        if (!BuildConfig.DEBUG) {
            require(BuildConfig.BUGSNAG_API_KEY.isNotBlank()) {
                "Bugsnag API key is blank!"
            }

            val client = Bugsnag.init(this, BuildConfig.BUGSNAG_API_KEY)
            client.setReleaseStage(BuildConfig.BUILD_TYPE)
            client.setProjectPackages("com.nicolasguillen.kointlin")
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
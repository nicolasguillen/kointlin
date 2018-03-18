package com.nicolasguillen.kointlin

import android.app.Application
import com.nicolasguillen.kointlin.di.ApplicationComponent
import com.nicolasguillen.kointlin.di.DaggerApplicationComponent
import com.nicolasguillen.kointlin.di.modules.ApplicationModule
import com.nicolasguillen.kointlin.di.modules.ModelModule
import com.nicolasguillen.kointlin.di.modules.UseCaseModule

class KointlinApp : Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .modelModule(ModelModule())
                .useCaseModule(UseCaseModule())
                .build()
    }

}
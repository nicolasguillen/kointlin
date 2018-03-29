package com.nicolasguillen.kointlin.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.nicolasguillen.kointlin.BuildConfig
import com.nicolasguillen.kointlin.services.ApiClient
import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.ApiService
import com.nicolasguillen.kointlin.storage.*
import com.nicolasguillen.kointlin.storage.dao.AppSettingsDao
import com.nicolasguillen.kointlin.storage.dao.WalletDao
import com.nicolasguillen.kointlin.storage.migrations.MIGRATION_5_6
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    internal open fun provideApiClient(apiService: ApiService,
                                       gson: Gson): ApiRepository {
        return ApiClient(apiService, gson)
    }

    @Provides
    internal fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Only log in debug mode to avoid leaking sensitive information.
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        builder.connectTimeout(10, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    internal fun provideApiService(gson: Gson,
                                   okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.coinmarketcap.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    internal fun providesAppDatabase(): KointlinDatabase {
        return Room.databaseBuilder(application, KointlinDatabase::class.java, "kointlin-db")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_5_6)
                .build()
    }

    @Provides
    internal fun providesWalletDao(database: KointlinDatabase) = database.walletDao()

    @Provides
    internal open fun providesWalletClient(walletDao: WalletDao): WalletRepository {
        return WalletClient(walletDao)
    }

    @Provides
    internal fun providesAppSettingsDao(database: KointlinDatabase) = database.appSettingsDao()

    @Provides
    internal open fun providesAppSettingsClient(appSettingsDao: AppSettingsDao): AppSettingsRepository {
        return AppSettingsClient(appSettingsDao)
    }
}
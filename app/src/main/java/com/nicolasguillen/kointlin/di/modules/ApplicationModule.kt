package com.nicolasguillen.kointlin.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.nicolasguillen.kointlin.BuildConfig
import com.nicolasguillen.kointlin.services.ApiClient
import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.ApiService
import com.nicolasguillen.kointlin.storage.WalletClient
import com.nicolasguillen.kointlin.storage.WalletDao
import com.nicolasguillen.kointlin.storage.WalletDatabase
import com.nicolasguillen.kointlin.storage.WalletRepository
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
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    internal fun provideApiClient(apiService: ApiService,
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
    internal fun provideApiRetrofit(gson: Gson,
                                    okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://coincap.io")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    internal fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
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
    internal fun providesAppDatabase(): WalletDatabase {
        return Room.databaseBuilder(application, WalletDatabase::class.java, "wallet-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    internal fun providesWalletDao(database: WalletDatabase) = database.walletDao()

    @Provides
    internal fun providesWalletClient(walletDao: WalletDao): WalletRepository {
        return WalletClient(walletDao)
    }

}
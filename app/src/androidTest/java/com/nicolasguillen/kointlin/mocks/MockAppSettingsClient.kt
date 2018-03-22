package com.nicolasguillen.kointlin.mocks

import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import com.nicolasguillen.kointlin.storage.entities.AppSettings
import io.reactivex.Single

class MockAppSettingsClient : AppSettingsRepository {
    override fun getAppSettings(): Single<AppSettings> {
        return Single.just(
                AppSettings("1", "USD")
        )
    }

    override fun saveAppSettings(appSettings: AppSettings) {}

    override fun removeAll() {}

}

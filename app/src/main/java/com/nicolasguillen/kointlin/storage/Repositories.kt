package com.nicolasguillen.kointlin.storage

import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.storage.entities.AppSettings
import io.reactivex.Single

interface WalletRepository {

    fun getAllAssets(): Single<List<Asset>>

    fun getAssetByShortName(shortName: String): Single<Asset>

    fun insertAsset(asset: Asset)

    fun updateAsset(asset: Asset)

    fun deleteAsset(asset: Asset)
}

interface AppSettingsRepository {

    fun getAppSettings(): Single<AppSettings>

    fun saveAppSettings(appSettings: AppSettings)

    fun removeAll()
}
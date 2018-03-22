package com.nicolasguillen.kointlin.storage

import android.arch.persistence.room.EmptyResultSetException
import com.nicolasguillen.kointlin.storage.dao.AppSettingsDao
import com.nicolasguillen.kointlin.storage.dao.WalletDao
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.storage.entities.AppSettings
import io.reactivex.Single

class WalletClient(private val walletDao: WalletDao): WalletRepository {

    override fun getAllAssets() = walletDao.getAllAssets()

    override fun getAssetByShortName(shortName: String) = walletDao.getAssetByShortName(shortName)

    override fun insertAsset(asset: Asset) = walletDao.insertAsset(asset)

    override fun updateAsset(asset: Asset) = walletDao.updateAsset(asset)

    override fun deleteAsset(asset: Asset) = walletDao.deleteAsset(asset)
}

class AppSettingsClient(private val appSettingsDao: AppSettingsDao): AppSettingsRepository {

    override fun getAppSettings(): Single<AppSettings> {
        return Single.create { observer ->
            appSettingsDao.getAppSettings()
                    .subscribe(
                            { userSettings -> observer.onSuccess(userSettings) },
                            { error -> when(error){
                                is EmptyResultSetException ->
                                    observer.onSuccess(AppSettings("1", "USD"))
                            } }
                    )
        }
    }

    override fun saveAppSettings(appSettings: AppSettings) = appSettingsDao.saveAppSettings(appSettings)

    override fun removeAll() = appSettingsDao.removeAll()

}
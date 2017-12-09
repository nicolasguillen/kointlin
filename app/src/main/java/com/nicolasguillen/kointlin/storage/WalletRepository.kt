package com.nicolasguillen.kointlin.storage

import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Flowable

interface WalletRepository {

    fun getAllAssets(): Flowable<List<Asset>>

    fun getAssetByShortName(shortName: String): Flowable<Asset>

    fun insertAsset(asset: Asset)

    fun updateAsset(asset: Asset)

    fun deleteAsset(asset: Asset)
}

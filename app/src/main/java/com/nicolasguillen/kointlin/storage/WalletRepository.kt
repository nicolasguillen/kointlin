package com.nicolasguillen.kointlin.storage

import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Single

interface WalletRepository {

    fun getAllAssets(): Single<List<Asset>>

    fun getAssetByShortName(shortName: String): Single<Asset>

    fun insertAsset(asset: Asset)

    fun updateAsset(asset: Asset)

    fun deleteAsset(asset: Asset)
}

package com.nicolasguillen.kointlin.mocks

import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Single

class MockWalletClient : WalletRepository {
    override fun getAllAssets(): Single<List<Asset>> {
        return Single.just(listOf(
                Asset("BTC", "BTC", "Bitcoin", 1.0),
                Asset("ETH", "ETH", "Etherium", 2.0)
        ))
    }

    override fun getAssetByShortName(shortName: String): Single<Asset> {
        return Single.just(
                when(shortName) {
                    "BTC" -> Asset("BTC", "BTC", "Bitcoin", 1.0)
                    "ETH" -> Asset("ETH", "ETH", "Etherium", 2.0)
                    else -> Asset(shortName, shortName, shortName, 0.0)
                }
        )
    }

    override fun insertAsset(asset: Asset) {}

    override fun updateAsset(asset: Asset) {}

    override fun deleteAsset(asset: Asset) {}

}

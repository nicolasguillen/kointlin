package com.nicolasguillen.kointlin.storage

import com.nicolasguillen.kointlin.storage.entities.Asset

class WalletClient(private val walletDao: WalletDao): WalletRepository {

    override fun getAllAssets() = walletDao.getAllAssets()

    override fun getAssetByShortName(shortName: String) = walletDao.getAssetByShortName(shortName)

    override fun insertAsset(asset: Asset) = walletDao.insertAsset(asset)

    override fun updateAsset(asset: Asset)  = walletDao.updateAsset(asset)

    override fun deleteAsset(asset: Asset) = walletDao.deleteAsset(asset)
}
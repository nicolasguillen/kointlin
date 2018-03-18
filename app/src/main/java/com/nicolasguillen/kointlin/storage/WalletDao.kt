package com.nicolasguillen.kointlin.storage

import android.arch.persistence.room.*
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Single

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallet")
    fun getAllAssets(): Single<List<Asset>>
    
    @Query("SELECT * FROM wallet WHERE short_name = :shortName")
    fun getAssetByShortName(shortName: String): Single<Asset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsset(asset: Asset)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAsset(asset: Asset)

    @Delete
    fun deleteAsset(asset: Asset)
}
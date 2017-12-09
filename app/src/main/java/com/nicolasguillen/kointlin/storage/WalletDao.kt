package com.nicolasguillen.kointlin.storage

import android.arch.persistence.room.*
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Flowable

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallet")
    fun getAllAssets(): Flowable<List<Asset>>

    @Query("SELECT * FROM wallet WHERE short_name = :shortName")
    fun getAssetByShortName(shortName: String): Flowable<Asset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsset(asset: Asset)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAsset(asset: Asset)

    @Delete
    fun deleteAsset(asset: Asset)
}
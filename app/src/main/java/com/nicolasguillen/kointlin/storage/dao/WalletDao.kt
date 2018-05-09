package com.nicolasguillen.kointlin.storage.dao

import androidx.room.*
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Single
import org.intellij.lang.annotations.Language

@Dao
interface WalletDao {

    @Language("RoomSql")
    @Query("SELECT * FROM wallet")
    fun getAllAssets(): Single<List<Asset>>

    @Language("RoomSql")
    @Query("SELECT * FROM wallet WHERE short_name = :shortName")
    fun getAssetByShortName(shortName: String): Single<Asset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsset(asset: Asset)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAsset(asset: Asset)

    @Delete
    fun deleteAsset(asset: Asset)
}
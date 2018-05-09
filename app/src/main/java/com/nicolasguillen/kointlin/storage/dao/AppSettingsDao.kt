package com.nicolasguillen.kointlin.storage.dao

import androidx.room.*
import com.nicolasguillen.kointlin.storage.entities.AppSettings
import io.reactivex.Single
import org.intellij.lang.annotations.Language

@Dao
interface AppSettingsDao {

    @Language("RoomSql")
    @Query("SELECT * FROM appSettings WHERE id = 1")
    fun getAppSettings(): Single<AppSettings>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAppSettings(appSettings: AppSettings)

    @Language("RoomSql")
    @Query("DELETE FROM appSettings")
    fun removeAll()
}
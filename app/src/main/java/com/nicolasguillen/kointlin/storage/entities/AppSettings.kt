package com.nicolasguillen.kointlin.storage.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "appSettings")
data class AppSettings(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "currency_code")
        var currencyCode: String
)
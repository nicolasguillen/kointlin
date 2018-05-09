package com.nicolasguillen.kointlin.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appSettings")
data class AppSettings(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "currency_code")
        var currencyCode: String
)
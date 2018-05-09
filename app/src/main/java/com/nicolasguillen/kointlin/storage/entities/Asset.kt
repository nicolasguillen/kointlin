package com.nicolasguillen.kointlin.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet")
data class Asset(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "short_name")
        val shortName: String,
        @ColumnInfo(name = "long_name")
        val longName: String,
        @ColumnInfo(name = "amount")
        val amount: Double)
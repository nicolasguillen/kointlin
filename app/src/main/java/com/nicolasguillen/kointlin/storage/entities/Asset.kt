package com.nicolasguillen.kointlin.storage.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "wallet")
data class Asset(
        @ColumnInfo(name = "short_name")
        @PrimaryKey
        val shortName: String,
        @ColumnInfo(name = "long_name")
        val longName: String,
        @ColumnInfo(name = "amount")
        val amount: Double)
package com.nicolasguillen.kointlin.storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.nicolasguillen.kointlin.storage.entities.Asset

@Database(entities = [Asset::class], version = 3, exportSchema = false)
abstract class WalletDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao
}
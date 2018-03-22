package com.nicolasguillen.kointlin.storage.migrations

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

val MIGRATION_5_6: Migration = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Since we didn't alter the table, there's nothing else to do here.
    }
}
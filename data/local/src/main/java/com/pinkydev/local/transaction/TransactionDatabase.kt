package com.pinkydev.local.transaction

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TransactionLocalDto::class],
    version = 2,
    exportSchema = false
)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
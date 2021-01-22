package com.pinkydev.local.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * from transaction_table ORDER BY date DESC")
    fun getTransactions(): Flow<List<TransactionLocalDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(list: List<TransactionLocalDto>)
}
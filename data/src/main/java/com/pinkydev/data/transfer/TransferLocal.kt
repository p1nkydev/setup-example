package com.pinkydev.data.transfer

import com.pinkydev.domain.model.transaction.TransactionHistory
import kotlinx.coroutines.flow.Flow

interface TransferLocal {
    fun getTransactions(id: Long): Flow<List<TransactionHistory>>
    suspend fun saveTransaction(transaction: Transaction)
}
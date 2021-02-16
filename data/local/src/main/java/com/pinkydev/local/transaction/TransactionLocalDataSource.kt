package com.pinkydev.local.transaction


import com.pinkydev.data.transfer.Transaction
import com.pinkydev.data.transfer.TransferLocal
import com.pinkydev.domain.model.transaction.TransactionHistory
import com.pinkydev.local.mapper.toDomain
import com.pinkydev.local.mapper.toLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionLocalDataSource(
    private val transactionDao: TransactionDao,
) : TransferLocal {

    override fun getTransactions(id: Long): Flow<List<TransactionHistory>> {
        return transactionDao.getTransactions().map { it.toDomain() }
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        transactionDao.insertTransactions(transaction.toLocal())
    }
}
package com.pinkydev.data.repository

import com.pinkydev.data.transfer.TransferLocal
import com.pinkydev.data.transfer.TransferRemote
import com.pinkydev.domain.model.card.DigitalCard
import com.pinkydev.domain.model.card.MoneyAmount
import com.pinkydev.domain.model.card.ReceiverDigitalCard
import com.pinkydev.domain.repository.TransactionRepository
import com.pinkydev.domain.model.transaction.TransactionHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val remote: TransferRemote,
    private var local: TransferLocal,
) : TransactionRepository {

    override fun getTransactionHistory(digitalCard: DigitalCard): Flow<List<TransactionHistory>> {
        return local.getTransactions(digitalCard.id).map {
            if (it.isEmpty()) remote.getTransactions() else it
        }
    }

    override suspend fun transfer(amount: MoneyAmount, to: ReceiverDigitalCard) {
        remote.transfer(amount.balance, to.phoneNumber).also {
            local.saveTransaction(it)
        }
    }

}
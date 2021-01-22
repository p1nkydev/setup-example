package com.pinkydev.domain.repository

import com.pinkydev.domain.model.card.DigitalCard
import com.pinkydev.domain.model.card.MoneyAmount
import com.pinkydev.domain.model.card.ReceiverDigitalCard
import com.pinkydev.domain.model.transaction.TransactionHistory
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactionHistory(digitalCard: DigitalCard): Flow<List<TransactionHistory>>
    suspend fun transfer(amount: MoneyAmount, to: ReceiverDigitalCard)
}
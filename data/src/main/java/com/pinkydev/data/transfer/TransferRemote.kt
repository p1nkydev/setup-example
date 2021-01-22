package com.pinkydev.data.transfer

import com.pinkydev.domain.model.transaction.TransactionHistory
import java.math.BigDecimal

interface TransferRemote {
    suspend fun getTransactions(): List<TransactionHistory>
    suspend fun transfer(amount: BigDecimal, phoneNumber: String): Transaction
}
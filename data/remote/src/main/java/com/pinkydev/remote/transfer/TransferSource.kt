package com.pinkydev.remote.transfer

import com.pinkydev.data.transfer.Transaction
import com.pinkydev.data.transfer.TransferRemote
import com.pinkydev.domain.model.card.MoneyAmount
import com.pinkydev.domain.model.transaction.TransactionHistory
import com.pinkydev.remote.transfer.model.TransferRequest
import java.math.BigDecimal

class TransferSource(private val api: TransfersApi) : TransferRemote {

    override suspend fun transfer(amount: BigDecimal, phoneNumber: String): Transaction {
        return api.transfer(TransferRequest(amount.toString(), phoneNumber)).toData()
    }

    override suspend fun getTransactions(): List<TransactionHistory> {
        return api.getTransfers().toData()
    }
}

private fun List<com.pinkydev.remote.transfer.model.Transaction>.toData(): List<TransactionHistory> {
    return map {
        TransactionHistory("bla", "bla", "bla", MoneyAmount(BigDecimal.ONE))
    }
}


// todo move to mapper
private fun com.pinkydev.remote.transfer.model.Transaction.toData(): Transaction {
    return Transaction(transactionNo, datetime, currency, amount, merchant, description)
}

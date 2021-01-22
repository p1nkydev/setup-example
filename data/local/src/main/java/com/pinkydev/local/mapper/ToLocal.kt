package com.pinkydev.local.mapper

import com.pinkydev.data.transfer.Transaction
import com.pinkydev.local.transaction.TransactionLocalDto

fun Transaction.toLocal(): List<TransactionLocalDto> {
    return listOf(
        TransactionLocalDto(
            transactionNo ?: "",
            datetime ?: "",
            currency ?: "",
            amount ?: "",
            merchant ?: "",
            description ?: "",
            0
        )
    )
}
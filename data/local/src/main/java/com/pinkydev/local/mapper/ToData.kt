package com.pinkydev.local.mapper

import com.pinkydev.local.transaction.TransactionLocalDto
import com.pinkydev.domain.model.transaction.TransactionHistory

fun List<TransactionLocalDto>.toData() =
    map { TransactionHistory(it.merchant, it.merchant, it.datetime, it.amount) }
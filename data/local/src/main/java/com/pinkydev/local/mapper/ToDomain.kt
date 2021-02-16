package com.pinkydev.local.mapper

import com.pinkydev.domain.model.card.MoneyAmount
import com.pinkydev.domain.model.transaction.TransactionHistory
import com.pinkydev.local.transaction.TransactionLocalDto

fun List<TransactionLocalDto>.toDomain() =
    map { TransactionHistory(it.merchant, it.merchant, it.datetime, MoneyAmount(it.amount.toBigDecimal())) }
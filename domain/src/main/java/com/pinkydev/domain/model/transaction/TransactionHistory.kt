package com.pinkydev.domain.model.transaction

import com.pinkydev.domain.model.card.MoneyAmount


data class TransactionHistory(
    val userName: String,
    val userSurname: String,
    val date: String,
    val amount: MoneyAmount
)
package com.pinkydev.domain.model.card

import java.math.BigDecimal

data class MoneyAmount(
    val balance: BigDecimal,
    val currency: String = "AZN"
) {
    operator fun compareTo(amount: MoneyAmount) = balance.compareTo(amount.balance)
    operator fun compareTo(amount: BigDecimal) = balance.compareTo(amount)
}
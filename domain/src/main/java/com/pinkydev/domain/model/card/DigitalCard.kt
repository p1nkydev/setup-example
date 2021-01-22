package com.pinkydev.domain.model.card

data class DigitalCard(
    val id: Long,
    val cardholderName: String,
    val cardholderSurname: String,
    val maskedPan: String,
    val expiresAt: String,
    val moneyAmount: MoneyAmount
)
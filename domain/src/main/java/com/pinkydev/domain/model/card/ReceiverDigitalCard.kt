package com.pinkydev.domain.model.card

data class ReceiverDigitalCard(
    val cardholderName: String = "",
    val cardholderSurname: String ="",
    val maskedPan: String = "",
    val phoneNumber: String = "",
)
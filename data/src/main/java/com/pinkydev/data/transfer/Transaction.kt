package com.pinkydev.data.transfer

data class Transaction(
    val transactionNo: String? = null,
    val datetime: String? = null,
    var currency: String? = null,
    var amount: String? = null,
    var merchant: String? = null,
    var description: String? = null
)

package com.pinkydev.local.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "transaction_table",
    primaryKeys = ["transaction_no", "description", "amount"]
)
data class TransactionLocalDto(
    @ColumnInfo(name = "transaction_no") val transactionNo: String,
    @ColumnInfo(name = "date") val datetime: String,
    @ColumnInfo(name = "currency") var currency: String,
    @ColumnInfo(name = "amount") var amount: String,
    @ColumnInfo(name = "merchant") var merchant: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "card_id") var cardId: Long
)
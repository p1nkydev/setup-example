package com.pinkydev.remote.transfer.model

import kotlinx.serialization.Serializable

@Serializable
data class TransferRequest(val amount: String, val to: String)
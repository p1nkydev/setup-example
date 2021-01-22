package com.pinkydev.remote.transfer

import com.pinkydev.remote.transfer.model.Transaction
import com.pinkydev.remote.transfer.model.TransferRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransfersApi {

    @GET("/v1/transfers/transactions")
    suspend fun getTransfers(): List<Transaction>

    @POST("/v1/transfers")
    suspend fun transfer(@Body transferRequest: TransferRequest): Transaction
}
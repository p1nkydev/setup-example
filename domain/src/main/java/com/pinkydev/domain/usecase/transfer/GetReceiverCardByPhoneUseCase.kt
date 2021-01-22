package com.pinkydev.domain.usecase.transfer

import com.pinkydev.domain.base.BaseUseCase
import com.pinkydev.domain.exceptions.ErrorConverter
import com.pinkydev.domain.model.card.DigitalCard
import com.pinkydev.domain.model.transaction.TransactionHistory
import com.pinkydev.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class GetTransactionHistoryUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: TransactionRepository
) : BaseUseCase<GetTransactionHistoryUseCase.Params, Flow<List<TransactionHistory>>>(context, converter) {

    override suspend fun executeOnBackground(params: Params): Flow<List<TransactionHistory>> {
        return repository.getTransactionHistory(params.card)
    }

    class Params(val card: DigitalCard)
}
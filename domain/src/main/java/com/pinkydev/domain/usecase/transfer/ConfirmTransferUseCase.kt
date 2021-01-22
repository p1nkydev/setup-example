package com.pinkydev.domain.usecase.transfer

import com.pinkydev.domain.base.BaseUseCase
import com.pinkydev.domain.exceptions.ErrorConverter
import com.pinkydev.domain.model.card.MoneyAmount
import com.pinkydev.domain.model.card.ReceiverDigitalCard
import com.pinkydev.domain.repository.TransactionRepository
import kotlin.coroutines.CoroutineContext

class ConfirmTransferUseCase(
    context: CoroutineContext,
    errorConverter: ErrorConverter,
    private val repository: TransactionRepository
) : BaseUseCase<ConfirmTransferUseCase.Params, Unit>(context, errorConverter) {

    override suspend fun executeOnBackground(params: Params) {
        repository.transfer(params.amount, params.card)
    }

    class Params(val amount: MoneyAmount, val card: ReceiverDigitalCard)

}
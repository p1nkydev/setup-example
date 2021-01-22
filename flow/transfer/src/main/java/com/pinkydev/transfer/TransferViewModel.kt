package com.pinkydev.transfer

import com.pinkydev.domain.exceptions.ServerError
import com.pinkydev.domain.model.card.MoneyAmount
import com.pinkydev.domain.model.card.ReceiverDigitalCard
import com.pinkydev.domain.usecase.transfer.ConfirmTransferUseCase
import com.pinkydev.presentation.base.BaseViewModel
import java.math.BigDecimal

class TransferViewModel(
    private val confirmTransferUseCase: ConfirmTransferUseCase
) : BaseViewModel<TransferState, TransferEffect>() {


    fun onContinueClick() {
        postEffect(TransferEffect.ShowConfirmDialog())
    }

    fun onConfirmClick() {
        val receiverDigitalCard = ReceiverDigitalCard()
        val moneyAmount = MoneyAmount(BigDecimal.TEN)
        val params = ConfirmTransferUseCase.Params(moneyAmount, receiverDigitalCard)
        confirmTransferUseCase.launch(params) {
            onSuccess = {
                postState(TransferState.Success())
            }
            onError = {
                if (it is ServerError.UserBlocked) {
                    //todo handle specific error
                } else {
                    postState(TransferState.Failed())
                }
            }
        }
    }

}

sealed class TransferState {
    class Failed : TransferState()
    class Success : TransferState()
}

sealed class TransferEffect {
    class ShowConfirmDialog : TransferEffect()
    class AskSomething : TransferEffect()
}
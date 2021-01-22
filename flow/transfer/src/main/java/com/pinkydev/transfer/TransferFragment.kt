package com.pinkydev.transfer

import com.pinkydev.presentation.base.BaseFragment
import com.pinkydev.transfer.databinding.FragmentTransferBinding

class TransferFragment :
    BaseFragment<TransferState, TransferEffect, TransferViewModel, FragmentTransferBinding>(
        R.layout.fragment_transfer,
        TransferViewModel::class,
        FragmentTransferBinding::bind
    ) {


    override val bindViews: FragmentTransferBinding.() -> Unit = {
        btnContinue.setOnClickListener {
            viewModel.onContinueClick()
        }

        btnConfirm.setOnClickListener {
            viewModel.onConfirmClick()
        }
    }

}
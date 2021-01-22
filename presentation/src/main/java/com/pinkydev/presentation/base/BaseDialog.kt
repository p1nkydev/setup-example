package com.pinkydev.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.pinkydev.presentation.R
import com.pinkydev.presentation.databinding.DialogBaseBinding
import com.pinkydev.presentation.delegate.viewBinding

class BaseDialog(
    private val title: String = "",
    private val text: String = "",
    private val image: Int = android.R.drawable.btn_minus,
    private val scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER,
    private val cancelable: Boolean = true,
    private val buttonText: String = "",
    private val action: (BaseDialog) -> Unit = {},
    private val imageBg: Int = android.R.color.white
) : DialogFragment() {

    constructor() : this(
        //todo remove this and make more elegant
        title = "",
        text = "",
        image = android.R.drawable.btn_minus,
        scaleType = ImageView.ScaleType.CENTER,
        cancelable = true,
        buttonText = "",
        action = {},
        imageBg = android.R.color.white,
    )

    private val binding by viewBinding(DialogBaseBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_base, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo use real layout and bind data
//        binding.title.text = title
//        binding.text.text = text
//        binding.image.setBackgroundResource(imageBg)
//        binding.image.setImageResource(image)
//        binding.image.scaleType = scaleType
//        isCancelable = cancelable
//        binding.button.text = buttonText
//        binding.button.setOnClickListener {
//            action(this)
//        }
    }

    companion object {
        fun build(
            title: String = "",
            text: String = "",
            image: Int = android.R.drawable.ic_delete,
            scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER,
            cancelable: Boolean = true,
            buttonText: String = "",
            action: (BaseDialog) -> Unit = {},
            imageBg: Int = android.R.color.white
        ): BaseDialog {
            return BaseDialog(
                title,
                text,
                image,
                scaleType,
                cancelable,
                buttonText,
                action,
                imageBg
            )
        }
    }
}
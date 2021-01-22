package com.pinkydev.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.pinkydev.presentation.databinding.DialogBaseBinding
import com.pinkydev.presentation.delegate.viewBinding

class LoadingDialog : DialogFragment() {

    private val binding by viewBinding(DialogBaseBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(android.R.layout.list_content, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo use real layout and bind views
//        arguments?.getString(TITLE)?.let {
//            binding.title.text = it
//        }
//        arguments?.getString(TEXT)?.let {
//            binding.text.text = it
//        }
//        isCancelable = false
    }

    companion object {
        private const val TITLE = "title"
        private const val TEXT = "text"

        fun build(title: String? = null, text: String? = null) = LoadingDialog().apply {
            arguments = bundleOf(TITLE to title, TEXT to text)
        }
    }
}
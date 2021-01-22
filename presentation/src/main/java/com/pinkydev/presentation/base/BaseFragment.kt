package com.pinkydev.presentation.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.pinkydev.presentation.delegate.viewBinding
import com.pinkydev.presentation.tools.LogoutHandler
import com.pinkydev.presentation.tools.NavigationCommand
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseFragment<State, Effect, ViewModel : BaseViewModel<State, Effect>, B : ViewBinding>(
    @LayoutRes res: Int,
    vmClazz: KClass<ViewModel>,
    binding: (View) -> B
) : Fragment(res) {

    protected open val viewModel by viewModel<ViewModel>(vmClazz) { parametersOf(arguments) }
    protected val binding by viewBinding(binding)

    protected open val bindViews: B.() -> Unit = {}

    protected open val loadingDialog: DialogFragment? by lazy { LoadingDialog.build() }

    protected open val noInternetDialog: DialogFragment? by lazy {
        BaseDialog.build(
            title = "No Internet",
            action = { it.dismiss() }
        )
    }

    protected open fun observeState(state: State) {
        // override when observing
    }

    protected open fun observeEffect(effect: Effect) {
        // override when observing
    }

    protected open fun showNoInternet() {
        if (noInternetDialog?.isAdded == false) noInternetDialog?.show()
    }

    protected open fun showBackEndError() {
        BaseDialog.build(
            title = getString(android.R.string.copy),
            text = getString(android.R.string.copy),
            cancelable = false,
            buttonText = getString(android.R.string.copy),
            action = { it.dismiss() }
        ).show()
    }

    protected open fun showLoading() {
        if (loadingDialog?.isAdded == false) loadingDialog?.show()
    }

    protected open fun hideLoading() {
        if (loadingDialog?.isAdded == true) loadingDialog?.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard()
        bindViews(binding)
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::observeState)
        viewModel.effect.observe(viewLifecycleOwner, ::observeEffect)
        viewModel.commonEffect.observe(viewLifecycleOwner) {
            when (it) {
                is NoInternet -> showBackEndError()// todo display no internet "showNoInternet()"
                is BackEndError -> showBackEndError()
                is UnknownError -> showBackEndError()
                is ForceLogout -> handleForceLogout()
                is ShowGeneralLoading -> showLoading()
                is HideGeneralLoading -> hideLoading()
                else -> error("Unknown BaseEffect: $it")
            }
        }

        viewModel.navigationCommands.observe(this) { command ->
            when (command) {
                is NavigationCommand.To -> {
                    command.extras?.let { extras ->
                        findNavController().navigate(command.directions, extras)
                    } ?: run { findNavController().navigate(command.directions) }
                }
                is NavigationCommand.BackTo -> findNavController().getBackStackEntry(command.destinationId)
                is NavigationCommand.Back -> findNavController().popBackStack()
                is NavigationCommand.ToRoot -> findNavController().popBackStack(
                    findNavController().backStack.first.destination.id,
                    false
                )
                else -> {
                    //todo handle unknown navigation
                }
            }
        }
    }

    protected open fun handleForceLogout() {
        (activity as? LogoutHandler)?.handleLogout()
    }

    protected fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(requireContext(), colorRes)
    }

    protected fun <T : DialogFragment> T.show(tag: String? = null): T {
        this.show(this@BaseFragment.parentFragmentManager, tag)
        return this
    }

    protected fun showLoadingDialog(message: String? = null): DialogFragment {
        return LoadingDialog
            .build(text = message)
            .show()
    }

    protected fun showMessageDialog(message: String): DialogFragment {
        val dialog = BaseDialog.build(
            title = message,
            cancelable = true,
            action = { it.dismiss() }
        )
        dialog.show(parentFragmentManager, null)
        return dialog
    }

    protected fun showServerDownDialog(): DialogFragment {
        val dialog = BaseDialog.build(
            title = getString(android.R.string.copy),
            cancelable = false,
            text = getString(android.R.string.copy),
            image = android.R.drawable.alert_light_frame,
            buttonText = getString(android.R.string.copy),
            action = { it.dismiss() }
        )
        dialog.show(parentFragmentManager, null)
        return dialog
    }

    protected fun hideKeyboard() {
        if (!this.isAdded) return

        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusedView = requireActivity().currentFocus ?: View(requireActivity())
        imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }

    protected fun showKeyboard() {
        if (!this.isAdded) return

        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusedView = requireActivity().currentFocus ?: View(requireActivity())
        imm.showSoftInput(focusedView, 0)
    }
}
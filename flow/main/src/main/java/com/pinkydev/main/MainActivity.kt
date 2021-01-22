package com.pinkydev.main

import androidx.appcompat.app.AppCompatActivity
import com.pinkydev.presentation.tools.LogoutHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), LogoutHandler {

    private val viewModel by viewModel<MainViewModel>()

    override fun onResume() {
        super.onResume()
        viewModel.checkAuthorization()
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveLastActiveTime()
    }

    override fun handleLogout() {
        // navigate login screen
    }

}
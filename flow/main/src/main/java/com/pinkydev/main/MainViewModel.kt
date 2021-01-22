package com.pinkydev.main

import com.pinkydev.domain.usecase.auth.GetAuthorizationStatusUseCase
import com.pinkydev.domain.usecase.auth.GetAuthorizationStatusUseCase.Status.Authorized
import com.pinkydev.domain.usecase.auth.GetAuthorizationStatusUseCase.Status.Unauthorized
import com.pinkydev.domain.usecase.auth.SaveLastActiveTimeUseCase
import com.pinkydev.presentation.base.BaseViewModel

class MainViewModel(
    private val statusUseCase: GetAuthorizationStatusUseCase,
    private val saveLastActiveTimeUseCase: SaveLastActiveTimeUseCase,
) : BaseViewModel<MainState, Nothing>() {

    fun saveLastActiveTime() {
        saveLastActiveTimeUseCase.launchNoLoading(Unit)
    }

    fun checkAuthorization() {
        statusUseCase.launchNoLoading(Unit) {
            onSuccess = {
                when (it) {
                    Authorized -> postState(MainState.Authorized())
                    Unauthorized -> postState(MainState.NotAuthorized())
                }
            }
            onError = { postState(MainState.NotAuthorized()) }
        }
    }
}

sealed class MainState {
    class NotAuthorized : MainState() // class instead of object to deallocate memory
    class Authorized : MainState()
}
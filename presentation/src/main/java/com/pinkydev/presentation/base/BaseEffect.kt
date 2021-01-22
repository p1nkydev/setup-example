package com.pinkydev.presentation.base

interface BaseEffect

abstract class BaseUiError : BaseEffect
class NoInternet : BaseUiError()
class BackEndError : BaseUiError()
class UnknownError(val cause: Throwable) : BaseUiError()
class ForceLogout(val cause: Throwable) : BaseUiError()

abstract class BaseLoading : BaseEffect
class ShowGeneralLoading : BaseLoading()
class HideGeneralLoading : BaseLoading()
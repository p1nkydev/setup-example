package com.pinkydev.remote.errors.error

import kotlinx.serialization.Serializable

@Serializable
class ServerProblemDescription(val code: String = "", val message: String = "")
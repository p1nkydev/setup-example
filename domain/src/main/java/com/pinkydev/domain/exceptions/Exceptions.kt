package com.pinkydev.domain.exceptions

import java.io.IOException

abstract class HandledException(
    cause: Throwable? = null,
    message: String? = null
) : IOException(message, cause)


class UnknownError(cause: Throwable?) : HandledException(cause)

class InvalidPhoneNumberException : HandledException(message = "Phone number is invalid")

class NetworkError(cause: Throwable?) : HandledException(cause)

sealed class ServerError(
    open val serverCode: String,
    open val serverMessage: String
) : HandledException() {

    data class UserBlocked(
        override val serverCode: String,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)

    data class WrongPassword(
        override val serverCode: String,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)

    data class NotAuthorized(
        override val serverCode: String,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)

    data class AccessTokenExpired(
        override val serverCode: String,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)

    data class ServerIsDown(
        override val serverCode: String,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)
}


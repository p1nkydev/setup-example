package com.pinkydev.remote.errors

import com.pinkydev.domain.exceptions.ErrorMapper
import com.pinkydev.domain.exceptions.NetworkError
import com.pinkydev.domain.exceptions.ServerError
import com.pinkydev.remote.errors.error.ServerProblemDescription
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RemoteErrorMapper : ErrorMapper {

    override fun mapError(e: Throwable): Throwable = when (e) {
        is HttpException -> mapHttpErrors(e)
        is SocketException,
        is SocketTimeoutException,
        is UnknownHostException -> NetworkError(e)
        else -> com.pinkydev.domain.exceptions.UnknownError(e)
    }

    private fun mapHttpErrors(error: HttpException): Throwable {

        val description = try {
            error
                .response()
                ?.errorBody()
                ?.string()
                ?.let { Json.decodeFromString<ServerProblemDescription>(it) }

        } catch (ex: Throwable) {
            null
        } ?: ServerProblemDescription()

        return when (error.code()) {
            401 -> {
                when (description.code) {
                    "error.auth.invalidCredentials" ->
                        ServerError.WrongPassword(description.code, description.message)

                    "error.auth.userBlocked" ->
                        ServerError.UserBlocked(description.code, description.message)

                    else ->
                        ServerError.NotAuthorized(description.code, description.message)
                }
            }
            403 -> {
                ServerError.AccessTokenExpired(description.code, description.message)
            }
            in 500..600 -> {
                ServerError.ServerIsDown(description.code, description.message)
            }
            else -> ServerError.ServerIsDown(description.code, description.message)
        }
    }
}
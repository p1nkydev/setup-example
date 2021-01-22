package com.pinkydev.domain.usecase.auth

import com.pinkydev.domain.base.BaseUseCase
import com.pinkydev.domain.entity.time.Time
import com.pinkydev.domain.entity.time.TimeManager
import com.pinkydev.domain.exceptions.ErrorConverter
import com.pinkydev.domain.repository.UserRepository
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class GetAuthorizationStatusUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val timeManager: TimeManager,
    private val repository: UserRepository,
) : BaseUseCase<Unit, GetAuthorizationStatusUseCase.Status>(context, converter) {

    override suspend fun executeOnBackground(params: Unit): Status {
        val lastActiveTime = repository.getLastActiveTime()
        val currentTime = timeManager.getCurrentTime()
        val inactiveTime = (currentTime - lastActiveTime)
        return if (inactiveTime > Time(LOGIN_EXPIRATION_TIME_MILLIS, TimeUnit.MILLISECONDS)) {
            Status.Unauthorized
        } else {
            Status.Authorized
        }
    }

    sealed class Status {
        object Authorized : Status()
        object Unauthorized : Status()
    }

    companion object {
        private const val LOGIN_EXPIRATION_TIME_MILLIS = 1000 * 60L // 1 minute
    }
}

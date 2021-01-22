package com.pinkydev.domain.usecase.auth

import com.pinkydev.domain.base.BaseUseCase
import com.pinkydev.domain.entity.time.TimeManager
import com.pinkydev.domain.exceptions.ErrorConverter
import com.pinkydev.domain.repository.UserRepository
import kotlin.coroutines.CoroutineContext

class SaveLastActiveTimeUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val timeManager: TimeManager,
    private val repository: UserRepository,
) : BaseUseCase<Unit, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Unit) {
        return repository.saveLastActiveTime(timeManager.getCurrentTime())
    }

}
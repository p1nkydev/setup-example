package com.pinkydev.domain.di

import com.pinkydev.domain.entity.time.TimeManager
import com.pinkydev.domain.exceptions.ErrorConverter
import com.pinkydev.domain.exceptions.ErrorConverterImpl
import com.pinkydev.domain.usecase.auth.GetAuthorizationStatusUseCase
import com.pinkydev.domain.usecase.auth.SaveLastActiveTimeUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext


const val IO_CONTEXT = "IO_CONTEXT"
const val ERROR_MAPPER_NETWORK = "ERROR_MAPPER_NETWORK"

val domainModule = module {

    single<CoroutineContext>(named(IO_CONTEXT)) { Dispatchers.IO }

    single<ErrorConverter> {
        ErrorConverterImpl(setOf(get(named(ERROR_MAPPER_NETWORK))))
    }


    factory { TimeManager() }
    factory {
        GetAuthorizationStatusUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            timeManager = get(),
            repository = get()
        )
    }

    factory {
        SaveLastActiveTimeUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            timeManager = get(),
            repository = get()
        )
    }
}

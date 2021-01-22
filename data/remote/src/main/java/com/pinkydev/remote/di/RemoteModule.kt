package com.pinkydev.remote.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pinkydev.data.transfer.TransferRemote
import com.pinkydev.domain.di.ERROR_MAPPER_NETWORK
import com.pinkydev.domain.exceptions.ErrorMapper
import com.pinkydev.remote.errors.RemoteErrorMapper
import com.pinkydev.remote.transfer.TransferSource
import com.pinkydev.remote.transfer.TransfersApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val networkModule = module {

    factory<ErrorMapper>(named(ERROR_MAPPER_NETWORK)) { RemoteErrorMapper() }


    //////////////////////////////////// NETWORK ////////////////////////////////////

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(
            if (getProperty("isDebug") == true.toString()) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .retryOnConnectionFailure(false)
            .callTimeout(2, TimeUnit.SECONDS)
            .connectTimeout(2, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(getProperty("host"))
            .addConverterFactory(Json {
                isLenient = true
                encodeDefaults = true
                ignoreUnknownKeys = true
                prettyPrint = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    factory<TransfersApi> { get<Retrofit>().create(TransfersApi::class.java) }

    factory<TransferRemote> { TransferSource(api = get()) }
}
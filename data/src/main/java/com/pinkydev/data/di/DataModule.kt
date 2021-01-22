package com.pinkydev.data.di

import com.pinkydev.data.repository.TransactionRepositoryImpl
import com.pinkydev.data.repository.UserRepositoryImpl
import com.pinkydev.domain.repository.TransactionRepository
import com.pinkydev.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<TransactionRepository> {
        TransactionRepositoryImpl(
            remote = get(),
            local = get()
        )
    }

    factory<UserRepository> {
        UserRepositoryImpl(
            userSettings = get(),
            authCache = get()
        )
    }

}
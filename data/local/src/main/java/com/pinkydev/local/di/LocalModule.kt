package com.pinkydev.local.di

import androidx.room.Room
import com.pinkydev.data.auth.AuthCache
import com.pinkydev.data.transfer.TransferLocal
import com.pinkydev.data.user.UserSettings
import com.pinkydev.local.auth.AuthOtpCacheImpl
import com.pinkydev.local.transaction.TransactionDatabase
import com.pinkydev.local.transaction.TransactionLocalDataSource
import com.pinkydev.local.user.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single<AuthCache> { AuthOtpCacheImpl() }

    factory<UserSettings> { UserPreferences(androidContext()) }

    factory { get<TransactionDatabase>().transactionDao() }

    factory<TransferLocal> { TransactionLocalDataSource(transactionDao = get()) }


    single {
        Room.databaseBuilder(
            androidContext(),
            TransactionDatabase::class.java,
            "transaction-db"
        )
            .build()
    }
}
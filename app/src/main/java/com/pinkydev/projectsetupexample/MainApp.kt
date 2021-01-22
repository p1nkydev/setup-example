package com.pinkydev.projectsetupexample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@App)
            properties(mapOf("host" to BuildConfig.API_URL)) //API-HOST
            modules(appComponent)
        }
    }

}

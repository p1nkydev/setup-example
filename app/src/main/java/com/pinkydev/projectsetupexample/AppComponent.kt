package com.pinkydev.projectsetupexample

import com.pinkydev.data.di.repositoryModule
import com.pinkydev.domain.di.domainModule
import com.pinkydev.local.di.localModule
import com.pinkydev.main.di.mainModule
import com.pinkydev.remote.di.networkModule

val appComponent = listOf(repositoryModule, localModule, networkModule, mainModule, domainModule)
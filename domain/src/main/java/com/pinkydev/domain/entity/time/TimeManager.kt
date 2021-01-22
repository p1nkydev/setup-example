package com.pinkydev.domain.entity.time

import java.util.concurrent.TimeUnit

class TimeManager {

    fun getCurrentTime(): Time = Time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)

}
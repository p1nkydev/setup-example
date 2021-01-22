package com.pinkydev.domain.entity.time

import java.util.concurrent.TimeUnit

data class Time(val value: Long, val unit: TimeUnit) {

    operator fun minus(time: Time): Time {
        return Time(value - time.value, TimeUnit.MILLISECONDS)
    }

    operator fun compareTo(time: Time): Int = this.value.compareTo(time.value)
}
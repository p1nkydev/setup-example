package com.pinkydev.domain.exceptions

fun interface ErrorConverter {
    fun convert(t: Throwable): Throwable
}
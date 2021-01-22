package com.pinkydev.domain.repository

import com.pinkydev.domain.entity.time.Time
import com.pinkydev.domain.model.user.User

interface UserRepository {
    fun getUser(): User
    fun getLastActiveTime(): Time
    fun saveLastActiveTime(time: Time)
    fun getUserPhone(): String
    fun saveUserPhone(phone: String, temporary: Boolean)
}
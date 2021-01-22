package com.pinkydev.data.repository

import com.pinkydev.data.auth.AuthCache
import com.pinkydev.data.user.UserSettings
import com.pinkydev.domain.entity.time.Time
import com.pinkydev.domain.model.user.User
import com.pinkydev.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userSettings: UserSettings,
    private val authCache: AuthCache
) : UserRepository {

    override fun getUser() = User(
        login = userSettings.login,
        name = userSettings.userName,
        surname = userSettings.userSurname,
        phoneNumber = userSettings.phoneNumber
    )

    override fun getLastActiveTime(): Time {
        return userSettings.lastActiveTime
    }

    override fun saveLastActiveTime(time: Time) {
        userSettings.lastActiveTime = time
    }

    override fun getUserPhone(): String = authCache.phoneNumber ?: userSettings.phoneNumber

    override fun saveUserPhone(phone: String, temporary: Boolean) {
        if (temporary) {
            authCache.phoneNumber = phone
        } else {
            userSettings.phoneNumber = phone
        }
    }
}
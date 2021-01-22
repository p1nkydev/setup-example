package com.pinkydev.local.user

import android.content.Context
import com.pinkydev.data.user.UserSettings
import com.pinkydev.domain.entity.time.Time
import com.pinkydev.domain.model.user.User
import com.pinkydev.local.BaseSecuredPreferences
import java.util.concurrent.TimeUnit

class UserPreferences(context: Context) : BaseSecuredPreferences(context), UserSettings {
    override val filename = "user_settings"

    override var login by StringValue("login", "")
    override var userName by StringValue("user_name", "")
    override var userSurname by StringValue("user_surname", "")
    override var phoneNumber by StringValue("phone_number", "")

    private var _timeCache by LongValue("last_active_time", System.currentTimeMillis())

    override var lastActiveTime: Time
        set(value) {
            _timeCache = value.value
        }
        get() = Time(_timeCache, TimeUnit.MILLISECONDS)


    override fun cacheUser(data: User) {
        login = data.login
        userName = data.name
        userSurname = data.surname
        phoneNumber = data.phoneNumber
    }

    override fun removeUser() {
        login = ""
        userName = ""
        userSurname = ""
        phoneNumber = ""
        lastActiveTime = Time(0, TimeUnit.MILLISECONDS)
    }

}
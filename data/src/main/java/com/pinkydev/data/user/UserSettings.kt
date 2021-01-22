package com.pinkydev.data.user

import com.pinkydev.domain.entity.time.Time
import com.pinkydev.domain.model.user.User

interface UserSettings {
    var login: String
    var userName: String
    var userSurname: String
    var phoneNumber: String
    var lastActiveTime: Time

    fun cacheUser(data: User)
    fun removeUser()
}
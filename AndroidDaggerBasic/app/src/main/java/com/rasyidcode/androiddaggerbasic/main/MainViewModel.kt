package com.rasyidcode.androiddaggerbasic.main

import com.rasyidcode.androiddaggerbasic.user.UserDataRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userDataRepository: UserDataRepository) {

    val welcomeText: String
        get() = "Hello ${userDataRepository.username}!"
    val notificationText: String
        get() = "You have ${userDataRepository.unreadNotification} unread notifications"

}
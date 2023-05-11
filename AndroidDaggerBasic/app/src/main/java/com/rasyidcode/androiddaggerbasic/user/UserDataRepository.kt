package com.rasyidcode.androiddaggerbasic.user

import javax.inject.Inject
import kotlin.random.Random

class UserDataRepository @Inject constructor(
    private val userManager: UserManager
) {

    val username: String
        get() = userManager.username

    var unreadNotification: Int

    init {
        unreadNotification = randomInt()
    }

    fun refreshUnreadNotification() {
        unreadNotification = randomInt()
    }

    private fun randomInt(): Int {
        return Random.nextInt(until = 100)
    }

}
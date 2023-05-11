package com.rasyidcode.androiddaggerbasic.user

import javax.inject.Inject
import kotlin.random.Random

// This object will have a unique instance in a Component that
// is annotated with @LoggedUserScope (i.e. only UserComponent in this case)
@LoggedUserScope
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
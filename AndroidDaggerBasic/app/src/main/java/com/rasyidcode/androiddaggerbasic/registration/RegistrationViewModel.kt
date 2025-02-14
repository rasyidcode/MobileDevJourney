package com.rasyidcode.androiddaggerbasic.registration

import com.rasyidcode.androiddaggerbasic.di.ActivityScope
import com.rasyidcode.androiddaggerbasic.user.UserManager
import javax.inject.Inject

// @Inject tells Dagger how to provide instances of this type
// Dagger also knows that UserManager is a dependency
@ActivityScope
class RegistrationViewModel @Inject constructor(
    val userManager: UserManager
) {

    private var username: String? = null
    private var password: String? = null
    private var acceptedTCs: Boolean? = null

    fun updateUserData(username: String, password: String) {
        this.username = username
        this.password = password
    }

    fun acceptTCs() {
        acceptedTCs = true
    }

    fun registerUser() {
        assert(username != null)
        assert(password != null)
        assert(acceptedTCs == true)

        userManager.registerUser(username!!, password!!)
    }

}
package com.rasyidcode.androiddaggerbasic.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rasyidcode.androiddaggerbasic.user.UserManager
import javax.inject.Inject

/**
 * LoginViewModel is the viewModel that [LoginActivity] uses to
 * obtain information of what to show on the screen and handle complex logic
 */
class LoginViewModel @Inject constructor(private val userManager: UserManager) {

    private val _loginState = MutableLiveData<LoginViewState>()
    val loginState: LiveData<LoginViewState>
        get() = _loginState

    fun login(username: String, password: String) {
        if (userManager.loginUser(username, password)) {
            _loginState.value = LoginViewState.LoginSuccess
        } else {
            _loginState.value = LoginViewState.LoginError
        }
    }

    fun unregister() {
        userManager.unregister()
    }

    fun getUsername(): String = userManager.username

}
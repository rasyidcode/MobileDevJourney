package com.rasyidcode.androiddaggerbasic.registration.enterdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

private const val MAX_LENGTH = 5

/**
 * EnterDetailsViewModel is the viewModel that [EnterDetailsFragment] uses to
 * obtain to validate user's input data
 */
class EnterDetailsViewModel @Inject constructor() {

    private val _enterDetailsState = MutableLiveData<EnterDetailsViewState>()
    val enterDetailsViewState: LiveData<EnterDetailsViewState>
        get() = _enterDetailsState

    fun validateInput(username: String, password: String) {
        when {
            username.length < MAX_LENGTH -> _enterDetailsState.value =
                EnterDetailsViewState.EnterDetailsError("Username has to be longer than 4 characters")

            password.length < MAX_LENGTH -> _enterDetailsState.value =
                EnterDetailsViewState.EnterDetailsError("Password has to be longer than 4 characters")

            else -> _enterDetailsState.value = EnterDetailsViewState.EnterDetailsSuccess
        }
    }
}
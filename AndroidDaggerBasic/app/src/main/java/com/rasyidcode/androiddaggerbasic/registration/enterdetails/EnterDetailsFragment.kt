package com.rasyidcode.androiddaggerbasic.registration.enterdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.rasyidcode.androiddaggerbasic.R
import com.rasyidcode.androiddaggerbasic.registration.RegistrationActivity
import com.rasyidcode.androiddaggerbasic.registration.RegistrationViewModel

class EnterDetailsFragment : Fragment() {

    /**
     * RegistrationViewModel is used to set the username and password information (attached to
     * Activity's lifecycle and shared between different fragments)
     * EnterDetailsViewModel is used to validate the user input (attached to this
     * Fragment's lifecycle)
     *
     * They could get combined but for the sake of the codelab, we're separating them so we have
     * different ViewModels with different lifecycles
     */
    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var enterDetailsViewModel: EnterDetailsViewModel

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var errorTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_enter_details, container, false)

        registrationViewModel = (activity as RegistrationActivity).registrationViewModel
        enterDetailsViewModel = EnterDetailsViewModel()
        enterDetailsViewModel.enterDetailsViewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is EnterDetailsViewState.EnterDetailsSuccess -> {
                    val username = usernameEditText.text.toString()
                    val password = passwordEditText.text.toString()
                    registrationViewModel.updateUserData(username, password)

                    (activity as RegistrationActivity).onDetailsEntered()
                }

                is EnterDetailsViewState.EnterDetailsError -> {
                    errorTextView.text = state.error
                    errorTextView.visibility = View.VISIBLE
                }
            }
        }

        setupViews(view)

        return view
    }

    private fun setupViews(view: View) {
        errorTextView = view.findViewById(R.id.text_error)

        usernameEditText = view.findViewById(R.id.edit_username)
        usernameEditText.doOnTextChanged { _, _, _, _ -> errorTextView.visibility = View.INVISIBLE }
        usernameEditText.setOnFocusChangeListener { _, _ ->
            Log.d("EnterDetailsFragment", "focusChange: ${usernameEditText.text}")

            usernameEditText.hint = if (usernameEditText.hasFocus()) {
                ""
            } else {
                if (usernameEditText.text.isEmpty()) {
                    getString(R.string.username)
                } else {
                    ""
                }
            }
        }

        passwordEditText = view.findViewById(R.id.edit_password)
        passwordEditText.doOnTextChanged { _, _, _, _ -> errorTextView.visibility = View.INVISIBLE }

        view.findViewById<Button>(R.id.btn_next).setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            enterDetailsViewModel.validateInput(username, password)
        }
    }

}

sealed class EnterDetailsViewState {
    object EnterDetailsSuccess : EnterDetailsViewState()
    data class EnterDetailsError(val error: String) : EnterDetailsViewState()
}
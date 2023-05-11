package com.rasyidcode.androiddaggerbasic.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.rasyidcode.androiddaggerbasic.MyApplication
import com.rasyidcode.androiddaggerbasic.R
import com.rasyidcode.androiddaggerbasic.main.MainActivity
import com.rasyidcode.androiddaggerbasic.registration.RegistrationActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    // LoginViewModel is provided by Dagger
    @Inject
    lateinit var loginViewModel: LoginViewModel
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        // Creates an instance of Login component by grabbing the factory from the app graph
        // and injects this activity to that Component
        (application as MyApplication).appComponent.loginComponent().create().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginViewState.LoginSuccess -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                is LoginViewState.LoginError -> errorTextView.visibility = View.VISIBLE
            }
        }

        errorTextView = findViewById(R.id.text_error)
        setupViews()
    }

    private fun setupViews() {
        val usernameEditText = findViewById<EditText>(R.id.edit_username)
        usernameEditText.isEnabled = true
        usernameEditText.setText(loginViewModel.getUsername())

        val passwordEditText = findViewById<EditText>(R.id.edit_password)
        passwordEditText.doOnTextChanged { _, _, _, _ -> errorTextView.visibility = View.INVISIBLE }

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            loginViewModel.login(usernameEditText.text.toString(), passwordEditText.text.toString())
        }
        findViewById<Button>(R.id.btn_unregister).setOnClickListener {
            loginViewModel.unregister()
            val intent = Intent(this, RegistrationActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
        }
    }

}

sealed class LoginViewState {
    object LoginSuccess : LoginViewState()
    object LoginError : LoginViewState()
}
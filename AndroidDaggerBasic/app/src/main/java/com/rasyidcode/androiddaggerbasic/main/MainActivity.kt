package com.rasyidcode.androiddaggerbasic.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.androiddaggerbasic.MyApplication
import com.rasyidcode.androiddaggerbasic.R
import com.rasyidcode.androiddaggerbasic.login.LoginActivity
import com.rasyidcode.androiddaggerbasic.registration.RegistrationActivity
import com.rasyidcode.androiddaggerbasic.settings.SettingsActivity
import com.rasyidcode.androiddaggerbasic.user.UserManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var mainViewModel: MainViewModel

    /**
     * If the User is not registered, RegistrationActivity will be launched,
     * If the User is not logged in, LoginActivity will be launched
     * else carry on with MainActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        val userManager = (application as MyApplication).appComponent.userManager()

        super.onCreate(savedInstanceState)
        if (!userManager.isUserLoggedIn()) {
            if (!userManager.isUserRegistered()) {
                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        } else {
            setContentView(R.layout.activity_main)
            // If the MainActivity needs to be displayed, we get the UserComponent
            // from the application graph and gets this Activity injected
            userManager.userComponent!!.inject(this)
            setupViews()
        }
    }

    private fun setupViews() {
        findViewById<TextView>(R.id.text_hello).text = mainViewModel.welcomeText
        findViewById<Button>(R.id.btn_settings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        findViewById<TextView>(R.id.text_notifications).text = mainViewModel.notificationText
    }

}
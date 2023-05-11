package com.rasyidcode.androiddaggerbasic.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.androiddaggerbasic.MyApplication
import com.rasyidcode.androiddaggerbasic.R
import com.rasyidcode.androiddaggerbasic.login.LoginActivity
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Gets the userManager from application graph to obtain the instance
        // of UserComponent and gets this Activity injected
        val userManager = (application as MyApplication).appComponent.userManager()
        userManager.userComponent!!.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupViews()
    }

    private fun setupViews() {
        findViewById<Button>(R.id.btn_refresh).setOnClickListener {
            settingsViewModel.refreshNotifications()
        }
        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            settingsViewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
        }
    }

}
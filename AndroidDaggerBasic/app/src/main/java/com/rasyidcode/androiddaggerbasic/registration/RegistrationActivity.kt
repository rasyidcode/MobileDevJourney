package com.rasyidcode.androiddaggerbasic.registration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.androiddaggerbasic.MyApplication
import com.rasyidcode.androiddaggerbasic.R
import com.rasyidcode.androiddaggerbasic.main.MainActivity
import com.rasyidcode.androiddaggerbasic.registration.enterdetails.EnterDetailsFragment
import com.rasyidcode.androiddaggerbasic.registration.termsandconditions.TermsAndConditionsFragment
import javax.inject.Inject

class RegistrationActivity : AppCompatActivity() {

    // Stores an instance of RegistrationComponent so that its Fragments can access it
    lateinit var registrationComponent: RegistrationComponent

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Creates an instance of Registration component by grabbing the factory from the app graph
        registrationComponent =
            (application as MyApplication).appComponent.registrationComponent().create()
        // Injects this activity to the just created registration component
        registrationComponent.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_holder, EnterDetailsFragment())
            .commit()
    }

    /**
     * Callback from EntersDetailFragment when username and password has been entered
     */
    fun onDetailsEntered() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, TermsAndConditionsFragment())
            .addToBackStack(TermsAndConditionsFragment::class.java.simpleName)
            .commit()
    }

    /**
     * Callback from T&CFragment when TCs have been accepted
     */
    fun onTermsAndConditionsAccepted() {
        registrationViewModel.registerUser()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }

}
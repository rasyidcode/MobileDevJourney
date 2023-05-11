package com.rasyidcode.androiddaggerbasic.registration.termsandconditions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.rasyidcode.androiddaggerbasic.MyApplication
import com.rasyidcode.androiddaggerbasic.R
import com.rasyidcode.androiddaggerbasic.registration.RegistrationActivity
import com.rasyidcode.androiddaggerbasic.registration.RegistrationViewModel
import javax.inject.Inject

class TermsAndConditionsFragment : Fragment() {

    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false)

        view.findViewById<Button>(R.id.btn_next).setOnClickListener {
            registrationViewModel.acceptTCs()
            (activity as RegistrationActivity).onTermsAndConditionsAccepted()

        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as RegistrationActivity).registrationComponent.inject(this)
    }

}
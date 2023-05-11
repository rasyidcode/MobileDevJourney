package com.rasyidcode.androiddaggerbasic.registration

import com.rasyidcode.androiddaggerbasic.di.ActivityScope
import com.rasyidcode.androiddaggerbasic.registration.enterdetails.EnterDetailsFragment
import com.rasyidcode.androiddaggerbasic.registration.termsandconditions.TermsAndConditionsFragment
import dagger.Subcomponent

// Definition of a Dagger subcomponent
@ActivityScope
@Subcomponent
interface RegistrationComponent {

    // Factory to create instances of RegistrationComponent
    @Subcomponent.Factory
    interface Factory {
        fun create() : RegistrationComponent
    }

    // Classes that can be injected by this Component
    fun inject(activity: RegistrationActivity)
    fun inject(fragment: EnterDetailsFragment)
    fun inject(fragment: TermsAndConditionsFragment)

}
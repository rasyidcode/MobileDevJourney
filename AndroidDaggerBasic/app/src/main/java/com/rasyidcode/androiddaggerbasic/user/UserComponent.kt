package com.rasyidcode.androiddaggerbasic.user

import com.rasyidcode.androiddaggerbasic.main.MainActivity
import com.rasyidcode.androiddaggerbasic.settings.SettingsActivity
import dagger.Subcomponent

// This object will have a unique instance in a Component that
// Classes annotated with @LoggedUserScope will have a unique instance in this Component
// Definition of a Dagger subcomponent
@LoggedUserScope
@Subcomponent
interface UserComponent {

    // Factory to create instances of UserComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }

    // Classes that can be injected by this Component
    fun inject(activity: MainActivity)
    fun inject(activity: SettingsActivity)

}
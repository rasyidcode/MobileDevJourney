package com.rasyidcode.androiddaggerbasic.login

import com.rasyidcode.androiddaggerbasic.di.ActivityScope
import dagger.Subcomponent

// Scope annotation that the LoginComponent uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
// Definition of a Dagger subcomponent
@Subcomponent
interface LoginComponent {

    // Factory to create instance of LoginComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    // Classes that can be be injected by this Component
    fun inject(activity: LoginActivity)

}
package com.rasyidcode.androiddaggerbasic.di

import android.content.Context
import com.rasyidcode.androiddaggerbasic.login.LoginComponent
import com.rasyidcode.androiddaggerbasic.main.MainActivity
import com.rasyidcode.androiddaggerbasic.registration.RegistrationComponent
import com.rasyidcode.androiddaggerbasic.settings.SettingsActivity
import com.rasyidcode.androiddaggerbasic.user.UserManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// Definition of a Dagger component
@Singleton
@Component(modules = [StorageModule::class, AppSubcomponents::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    // Types that can be retrieved from the graph
    fun registrationComponent(): RegistrationComponent.Factory
    fun loginComponent(): LoginComponent.Factory

    // Expose UserManager so that MainActivity and SettingsActivity
    // can access a particular instance of UserComponent
    fun userManager(): UserManager
}
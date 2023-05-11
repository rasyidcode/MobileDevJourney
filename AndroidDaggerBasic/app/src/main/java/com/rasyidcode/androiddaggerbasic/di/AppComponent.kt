package com.rasyidcode.androiddaggerbasic.di

import android.content.Context
import com.rasyidcode.androiddaggerbasic.main.MainActivity
import com.rasyidcode.androiddaggerbasic.registration.RegistrationComponent
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

    // Classes that can be injected by this Component
    fun registrationComponent(): RegistrationComponent.Factory
    fun inject(activity: MainActivity)

}
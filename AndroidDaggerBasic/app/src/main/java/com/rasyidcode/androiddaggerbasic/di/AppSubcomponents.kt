package com.rasyidcode.androiddaggerbasic.di

import com.rasyidcode.androiddaggerbasic.login.LoginComponent
import com.rasyidcode.androiddaggerbasic.registration.RegistrationComponent
import com.rasyidcode.androiddaggerbasic.user.UserComponent
import dagger.Module

@Module(
    subcomponents = [
        RegistrationComponent::class,
        LoginComponent::class,
        UserComponent::class]
)
class AppSubcomponents
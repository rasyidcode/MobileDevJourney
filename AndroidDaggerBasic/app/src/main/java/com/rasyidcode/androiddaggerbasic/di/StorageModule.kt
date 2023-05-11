package com.rasyidcode.androiddaggerbasic.di

import com.rasyidcode.androiddaggerbasic.storage.SharedPreferencesStorage
import com.rasyidcode.androiddaggerbasic.storage.Storage
import dagger.Binds
import dagger.Module

// Tells Dagger this is a Dagger module
// Because of @Binds, StorageModule needs to be an abstract class
@Module
abstract class StorageModule {

    // Makes Dagger provide SharedPreferencesStorage when Storage type is requested
    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage

}
package com.rasyidcode.androiddaggerbasic.di

import com.rasyidcode.androiddaggerbasic.storage.FakeStorage
import com.rasyidcode.androiddaggerbasic.storage.Storage
import dagger.Binds
import dagger.Module

// Override StorageModule in android tests
@Module
abstract class TestStorageModule {

    // Makes Dagger provide FakeStorage when a Storage type is requested
    @Binds
    abstract fun provideStorage(storage: FakeStorage): Storage

}
package com.rasyidcode.androiddaggerbasic

import com.rasyidcode.androiddaggerbasic.di.AppComponent
import com.rasyidcode.androiddaggerbasic.di.DaggerTestAppComponent

class MyTestApplication : MyApplication() {

    override fun initializeComponent(): AppComponent {
        // Creates a new TestAppComponent that injects fakes types
        return DaggerTestAppComponent.create()
    }

}
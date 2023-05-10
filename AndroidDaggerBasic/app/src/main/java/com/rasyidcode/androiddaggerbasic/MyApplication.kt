package com.rasyidcode.androiddaggerbasic

import android.app.Application

open class MyApplication : Application() {

    open val userManager by lazy { }

}
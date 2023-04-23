package com.rasyidcode.devbyteviewer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.devbyteviewer.R

/**
 * This is a single activity application that uses Navigation library. Content is displayed
 * by Fragments.
 */
class DevByteActivity : AppCompatActivity() {

    /**
     * onCreate called when the activity is starting. This is where most initialization
     * should go
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_byte)
    }

}
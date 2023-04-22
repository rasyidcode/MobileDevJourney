package com.rasyidcode.busschedule

import android.app.Application
import com.rasyidcode.busschedule.database.AppDatabase

class BusScheduleApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

}
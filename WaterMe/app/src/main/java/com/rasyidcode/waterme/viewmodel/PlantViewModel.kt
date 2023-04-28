package com.rasyidcode.waterme.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rasyidcode.waterme.data.DataSource
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class PlantViewModel(application: Application) : ViewModel() {

    val plants = DataSource.plants

    internal fun scheduleReminder(
        duration: Long,
        unit: TimeUnit,
        plantName: String
    ) {
        // TODO: create a Data instance with the plantName passed to it

        // TODO: Generate a OneTimeWorkRequest with the passed, time unit, and data
        //  instance

        // TODO: Enqueue the request as a unique work request
    }

    class PlantViewModelFactory(private val application: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                PlantViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}
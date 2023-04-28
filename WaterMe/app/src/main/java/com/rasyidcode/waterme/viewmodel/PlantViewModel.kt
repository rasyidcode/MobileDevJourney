package com.rasyidcode.waterme.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.rasyidcode.waterme.data.DataSource
import com.rasyidcode.waterme.worker.WaterReminderWorker
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class PlantViewModel(application: Application) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)

    val plants = DataSource.plants

    internal fun scheduleReminder(
        duration: Long,
        unit: TimeUnit,
        plantName: String
    ) {
        // create a Data instance with the plantName passed to it
        val dataBuilder = Data.Builder()
            .putString(WaterReminderWorker.nameKey, plantName)
            .build()

        // Generate a OneTimeWorkRequest with the passed, time unit, and data
        //  instance
        val workRequest = OneTimeWorkRequest.Builder(WaterReminderWorker::class.java)
            .setInputData(dataBuilder)
            .setInitialDelay(duration, unit)
            .build()

        // Enqueue the request as a unique work request
        workManager.enqueue(workRequest)
    }

    class PlantViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
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
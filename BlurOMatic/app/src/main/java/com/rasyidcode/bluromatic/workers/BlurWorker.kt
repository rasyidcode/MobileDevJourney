package com.rasyidcode.bluromatic.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rasyidcode.bluromatic.KEY_IMAGE_URI

private const val TAG = "BlurWorker"

//class BlurWorker(
//    context: Context,
//    params: WorkerParameters
//): Worker(context, params) {
////    override fun doWork(): Result {
////        val appContext = applicationContext
////
////        val resourceUri = inputData.getString(KEY_IMAGE_URI)
////
//////        makeStatusNotification()
////
////    }
//
//
//}
package com.rasyidcode.waterme.worker

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rasyidcode.waterme.BaseApplication
import com.rasyidcode.waterme.MainActivity
import com.rasyidcode.waterme.R

class WaterReminderWorker(
    ctx: Context,
    params: WorkerParameters
) : Worker(ctx, params) {

    // Arbitrary id number
    val notificationId = 17

    override fun doWork(): Result {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent
            .getActivity(applicationContext, 0, intent, 0)
        val plantName = inputData.getString(nameKey)
        val builder = NotificationCompat.Builder(applicationContext, BaseApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle("Water Me!")
            .setContentText("It's time to water your $plantName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        applicationContext as Activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        101
                    )
                }
            }
            notify(notificationId, builder.build())
        }

        return Result.success()
    }

    companion object {
        const val nameKey = "NAME"
    }
}
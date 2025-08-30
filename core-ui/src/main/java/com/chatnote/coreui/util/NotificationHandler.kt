package com.chatnote.coreui.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import chatnote.coreui.R
import javax.inject.Inject

class NotificationHandler @Inject constructor(
    private val context: Context
) {
    @SuppressLint("MissingPermission")
    fun show(title: String, message: String) {
        val channelId = "chatnote_default"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (true
        ) {
            NotificationManagerCompat.from(context).notify(0, notification)
        }
    }
}
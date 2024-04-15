package com.nasiat_muhib.classmate.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.app.TaskStackBuilder
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.nasiat_muhib.classmate.MainActivity
import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.core.Constants.NOTIFICATION_URI

class MyNotification (
    private val context: Context,
    private val title: String,
    private val message: String
) {
    private val channelId = "ClassMate100"
    private val channelName = "ClassMate Notifications"

    fun fireNotification() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )

        val intent = Intent(
            Intent.ACTION_VIEW,
            NOTIFICATION_URI.toUri(),
            context,
            MainActivity::class.java
        )
        val pendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1, PendingIntent.FLAG_IMMUTABLE)
        }

        val notificationBuilder = NotificationCompat
            .Builder(context, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)


        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(100, notificationBuilder.build())
    }
}
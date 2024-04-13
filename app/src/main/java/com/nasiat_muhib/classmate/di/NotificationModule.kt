package com.nasiat_muhib.classmate.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.nasiat_muhib.classmate.MainActivity
import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.core.Constants.NOTIFICATION_URI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {

    @Singleton
    @Provides
    fun providesNotificationBuilder(@ApplicationContext context: Context): NotificationCompat.Builder {
        val intent = Intent(
            Intent.ACTION_VIEW,
            NOTIFICATION_URI.toUri(),
            context,
            MainActivity::class.java
        )

        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1, PendingIntent.FLAG_IMMUTABLE)
        }

        return NotificationCompat.Builder(context, "ClassMate Channel Id")
            .setContentTitle("Welcome")
            .setContentText("Hello From ClassMate Notification")
            .setSmallIcon(R.drawable.notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
    }

    @Singleton
    @Provides
    fun providesNotificationManger (@ApplicationContext context: Context): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel("ClassMate Channel Id", "ClassMate Channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }
}
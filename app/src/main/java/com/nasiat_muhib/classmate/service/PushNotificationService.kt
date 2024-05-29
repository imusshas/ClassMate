package com.nasiat_muhib.classmate.service

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nasiat_muhib.classmate.MainActivity
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.strings.FCMToken
import com.nasiat_muhib.classmate.strings.NOTIFICATION_BODY
import com.nasiat_muhib.classmate.strings.NOTIFICATION_TITLE
import com.nasiat_muhib.classmate.strings.USERS_COLLECTION

class PushNotificationService : FirebaseMessagingService() {

    private val auth = Firebase.auth
    private val firestoreRef = Firebase.firestore


    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        val email = auth.currentUser?.email
        if (email != null) {
            firestoreRef.collection(USERS_COLLECTION).document(email)
                .update(FCMToken, token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: message received")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
           val notify = MyNotification(
               applicationContext,
               remoteMessage.data[NOTIFICATION_TITLE].toString(),
               remoteMessage.data[NOTIFICATION_BODY].toString()
           )

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermission()

                return
            }
            notify.fireNotification()
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted = ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!isGranted) {
                ActivityCompat.requestPermissions(
                    MainActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    Constants.REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }
    }

    companion object {
        const val TAG = "PushNotificationService"
    }
}
package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getUserFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.Notification
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.repository.NotificationRepository
import com.nasiat_muhib.classmate.strings.CLASS_CANCELLED
import com.nasiat_muhib.classmate.strings.EMAIL
import com.nasiat_muhib.classmate.strings.FCMToken
import com.nasiat_muhib.classmate.strings.NOTIFICATION_BODY
import com.nasiat_muhib.classmate.strings.NOTIFICATION_COLLECTION
import com.nasiat_muhib.classmate.strings.NOTIFICATION_TITLE
import com.nasiat_muhib.classmate.strings.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestoreRef: FirebaseFirestore,
) : NotificationRepository {

    private val usersCollection = firestoreRef.collection(USERS_COLLECTION)

    private val postUrl = "https://fcm.googleapis.com/fcm/send"
    private val fcmServerKey = "AAAAYuGXu6U:APA91bH8XRDWJG1oaBMa37qP0bG9oypXvI1260Z4oDJzDK0lAuzZGSjKkRmUGAxfNLYbxezNXTs8lvCMZug5aFzAIpVNTa0HKIH1ZpB7foNt6cwbetf9hJfKo9KZ_H4ZnJELLThasO1p"
    private val jsonType = "application/json"

    private val department = "Department:"
    private val code = "Course Code:"
    private val title = "Title:"

    private val sendTo = "to"
    private val notificationData = "data"
    private val authorization = "Authorization"
    private val key = "key"

    override fun updateToken(token: String): Flow<Boolean> = flow<Boolean> {
        auth.currentUser?.email?.let { email ->
            val currentUser = usersCollection.document(email).get().await()
            val user = getUserFromFirestoreDocument(currentUser)
            val userWithToken = user.copy(token = token)
            Log.d(TAG, "updateToken: $userWithToken")
            usersCollection.document(email).update(userWithToken.toMap()).await()
        }
    }.catch {
        Log.d(TAG, "updateToken: error: ${it.message}")
    }

    override fun getToken(): Flow<String> = callbackFlow {
        val snapshotListener = usersCollection.document(auth.currentUser?.email!!).addSnapshotListener { value, error ->
            if (value != null) {
                trySend(value[FCMToken].toString()).isSuccess
            }
        }
        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getToken: ${it.localizedMessage}")
    }

    override fun sendUpdateNotification(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun sendEventNotification(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun sendClassCancelNotification(
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String
    ): Flow<Boolean> = flow {
        val creationTime = System.currentTimeMillis()
        val client = OkHttpClient()
        val json = JSONObject()
        val notification = JSONObject()

        val notificationBody = "$department $courseDepartment\n$code $courseCode\n$title $courseTitle"

        notification.put(NOTIFICATION_TITLE, CLASS_CANCELLED)
        notification.put(NOTIFICATION_BODY, notificationBody)

        json.put(sendTo, token)
        json.put(notificationData, notification)

        val body = json.toString().toRequestBody(jsonType.toMediaTypeOrNull())
        val request = Request.Builder()
            .url(postUrl)
            .post(body)
            .addHeader(authorization, "$key=$fcmServerKey")
            .build()

        courseUsers.forEach { user ->
            val currentUser = usersCollection.document(user).get().await()
            currentUser?.data?.let {
                val fcmToken = it[FCMToken].toString()
//                if (fcmToken != token) {
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
//                println("FCM Notification sending failed: ${e.message}")
                            Log.d(TAG, "onFailure: ${e.message}")
                        }

                        override fun onResponse(call: Call, response: Response) {
//                println("FCM Notification sent successfully")
                            Log.d(TAG, "onResponse: FCM Notification sent successfully")
                        }
                    })

                    createNotification(
                        documentId = user,
                        creationTime = creationTime,
                        type = CLASS_CANCELLED,
                        courseDepartment = courseDepartment,
                        courseCode = courseCode,
                        courseTitle = courseTitle
                    )
//                }
            }
        }
    }


    private suspend fun createNotification(
        documentId: String,
        creationTime: Long,
        type: String,
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
    ) {
        val notification = Notification(
            creationTime = creationTime,
            type = type,
            courseDepartment = courseDepartment,
            courseCode = courseCode,
            courseTitle = courseTitle,
            isRead = false
        )
        usersCollection.document(documentId).collection(NOTIFICATION_COLLECTION).add(notification.toMap()).await()
    }


    companion object {
        const val TAG = "NotificationRepositoryImpl"
    }
}
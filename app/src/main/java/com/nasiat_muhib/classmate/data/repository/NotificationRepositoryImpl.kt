package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getNotificationFromFirestoreDocument
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getUserFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.Notification
import com.nasiat_muhib.classmate.domain.repository.NotificationRepository
import com.nasiat_muhib.classmate.strings.CLASS_CANCELLED
import com.nasiat_muhib.classmate.strings.CLASS_UPDATE
import com.nasiat_muhib.classmate.strings.FCMToken
import com.nasiat_muhib.classmate.strings.NEW_ASSIGNMENT
import com.nasiat_muhib.classmate.strings.NEW_TERM_TEST
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
    private val messagingRef: FirebaseMessaging,
) : NotificationRepository {

    private val usersCollection = firestoreRef.collection(USERS_COLLECTION)

    private val postUrl = "https://fcm.googleapis.com/fcm/send"
    private val fcmServerKey =
        "AAAAYuGXu6U:APA91bH8XRDWJG1oaBMa37qP0bG9oypXvI1260Z4oDJzDK0lAuzZGSjKkRmUGAxfNLYbxezNXTs8lvCMZug5aFzAIpVNTa0HKIH1ZpB7foNt6cwbetf9hJfKo9KZ_H4ZnJELLThasO1p"
    private val jsonType = "application/json"

    private val code = "Course Code:"
    private val title = "Title:"

    private val sendTo = "to"
    private val notificationData = "data"
    private val authorization = "Authorization"
    private val key = "key"

    override fun updateToken(): Flow<Boolean> = flow<Boolean> {
        var fcmToken: String? = null
        messagingRef.token.addOnCompleteListener { task ->
            task.result?.let {
                fcmToken = it
            }
        }.await()
        if (fcmToken != null) {
            auth.currentUser?.email?.let { email ->
                val currentUser = usersCollection.document(email).get().await()
                val user = getUserFromFirestoreDocument(currentUser)
                val userWithToken = user.copy(token = fcmToken!!)
                usersCollection.document(email).update(userWithToken.toMap()).await()
            }
        }
    }.catch {
        Log.d(TAG, "updateToken: error: ${it.message}")
    }

    override fun getToken(email: String): Flow<String> = callbackFlow {
        val snapshotListener = usersCollection.document(email)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    trySend(value[FCMToken].toString()).isSuccess
                    Log.d(TAG, "getToken: ${value[FCMToken].toString()}")
                }
            }
        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getToken: ${it.localizedMessage}")
    }

    override fun sendUpdateNotification(
        courseCreator: String,
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean> = flow {
        val client = OkHttpClient()
        val json = JSONObject()
        val notification = JSONObject()

        val notificationBody =
            "$code $courseCode\n$title $courseTitle"

        notification.put(NOTIFICATION_TITLE, CLASS_UPDATE)
        notification.put(NOTIFICATION_BODY, notificationBody)

        courseUsers.forEach { user ->
            val courseUser = usersCollection.document(user).get().await()
            courseUser?.data?.let {
                val fcmToken = it[FCMToken].toString()
                Log.d(TAG, "sendUpdateNotification: $fcmToken")
                if (fcmToken != token) {
                    json.put(sendTo, fcmToken)
                    json.put(notificationData, notification)
                    val body = json.toString().toRequestBody(jsonType.toMediaTypeOrNull())
                    val request = Request.Builder()
                        .url(postUrl)
                        .post(body)
                        .addHeader(authorization, "$key=$fcmServerKey")
                        .build()
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
                        type = CLASS_UPDATE,
                        courseDepartment = courseDepartment,
                        courseCreator = courseCreator,
                        courseCode = courseCode,
                        courseTitle = courseTitle
                    )
                }
            }
        }
    }

    override fun sendTermTestNotification(
        courseCreator: String,
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean> = flow {
        val client = OkHttpClient()
        val json = JSONObject()
        val notification = JSONObject()

        val notificationBody =
            "$code $courseCode\n$title $courseTitle"

        notification.put(NOTIFICATION_TITLE, NEW_TERM_TEST)
        notification.put(NOTIFICATION_BODY, notificationBody)

        courseUsers.forEach { user ->
            val courseUser = usersCollection.document(user).get().await()
            courseUser?.data?.let {
                val fcmToken = it[FCMToken].toString()
                if (fcmToken != token) {
                    json.put(sendTo, fcmToken)
                    json.put(notificationData, notification)
                    val body = json.toString().toRequestBody(jsonType.toMediaTypeOrNull())
                    val request = Request.Builder()
                        .url(postUrl)
                        .post(body)
                        .addHeader(authorization, "$key=$fcmServerKey")
                        .build()
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
                        type = NEW_TERM_TEST,
                        courseCreator = courseCreator,
                        courseDepartment = courseDepartment,
                        courseCode = courseCode,
                        courseTitle = courseTitle
                    )
                }
            }
        }
    }

    override fun sendAssignmentNotification(
        courseCreator: String,
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean> = flow {
        val client = OkHttpClient()
        val json = JSONObject()
        val notification = JSONObject()

        val notificationBody =
            "$code $courseCode\n$title $courseTitle"

        notification.put(NOTIFICATION_TITLE, NEW_ASSIGNMENT)
        notification.put(NOTIFICATION_BODY, notificationBody)

        courseUsers.forEach { user ->
            val courseUser = usersCollection.document(user).get().await()
            courseUser?.data?.let {
                val fcmToken = it[FCMToken].toString()
                if (fcmToken != token) {
                    json.put(sendTo, fcmToken)
                    json.put(notificationData, notification)
                    val body = json.toString().toRequestBody(jsonType.toMediaTypeOrNull())
                    val request = Request.Builder()
                        .url(postUrl)
                        .post(body)
                        .addHeader(authorization, "$key=$fcmServerKey")
                        .build()
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
                        type = NEW_ASSIGNMENT,
                        courseCreator = courseCreator,
                        courseDepartment = courseDepartment,
                        courseCode = courseCode,
                        courseTitle = courseTitle
                    )
                }
            }
        }
    }

    override fun sendClassCancelNotification(
        courseCreator: String,
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean> = flow {
        val client = OkHttpClient()
        val json = JSONObject()
        val notification = JSONObject()

        val notificationBody =
            "$code $courseCode\n$title $courseTitle"

        notification.put(NOTIFICATION_TITLE, CLASS_CANCELLED)
        notification.put(NOTIFICATION_BODY, notificationBody)


        courseUsers.forEach { user ->
            val courseUser = usersCollection.document(user).get().await()
            courseUser?.data?.let {
                val fcmToken = it[FCMToken].toString()
                Log.d(TAG, "sendClassCancelNotification: \ncurrentUserToken: $token \ncourseUserToken: $fcmToken")
                if (fcmToken != token) {
                    json.put(sendTo, fcmToken)
                    json.put(notificationData, notification)
                    val body = json.toString().toRequestBody(jsonType.toMediaTypeOrNull())
                    val request = Request.Builder()
                        .url(postUrl)
                        .post(body)
                        .addHeader(authorization, "$key=$fcmServerKey")
                        .build()
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
                        type = CLASS_CANCELLED,
                        courseCreator = courseCreator,
                        courseDepartment = courseDepartment,
                        courseCode = courseCode,
                        courseTitle = courseTitle
                    )
                }
            }
        }
    }

    override fun getNotifications(email: String): Flow<List<Notification>> = callbackFlow {

        val snapshotListener = usersCollection.document(email).collection(NOTIFICATION_COLLECTION)
            .addSnapshotListener { value, error ->
                val notifications = mutableListOf<Notification>()
                value?.documents?.forEach {
                    if (it != null) {
                        val notification = getNotificationFromFirestoreDocument(it)
                        notifications.add(notification)
                    }
                }

                trySend(notifications).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getNotifications: ${it.message}")
    }


    private suspend fun createNotification(
        documentId: String,
        type: String,
        courseDepartment: String,
        courseCreator: String,
        courseCode: String,
        courseTitle: String,
    ) {
        val creationTime = System.currentTimeMillis()
        val notification = Notification(
            creationTime = creationTime,
            type = type,
            courseDepartment = courseDepartment,
            courseCreator = courseCreator,
            courseCode = courseCode,
            courseTitle = courseTitle,
            isRead = false
        )
        usersCollection.document(documentId).collection(NOTIFICATION_COLLECTION)
            .add(notification.toMap()).await()
    }


    companion object {
        const val TAG = "NotificationRepositoryImpl"
    }
}
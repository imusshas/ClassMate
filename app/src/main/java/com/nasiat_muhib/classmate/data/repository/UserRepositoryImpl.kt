package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.nasiat_muhib.classmate.core.Constants.BLOOD_GROUP
import com.nasiat_muhib.classmate.core.Constants.DEPARTMENT
import com.nasiat_muhib.classmate.core.Constants.EMAIL
import com.nasiat_muhib.classmate.core.Constants.ENROLLED
import com.nasiat_muhib.classmate.core.Constants.FIRST_NAME
import com.nasiat_muhib.classmate.core.Constants.LAST_NAME
import com.nasiat_muhib.classmate.core.Constants.PASSWORD
import com.nasiat_muhib.classmate.core.Constants.PHONE_NO
import com.nasiat_muhib.classmate.core.Constants.REQUESTED
import com.nasiat_muhib.classmate.core.Constants.ROLE
import com.nasiat_muhib.classmate.core.Constants.SESSION
import com.nasiat_muhib.classmate.core.Constants.TAG
import com.nasiat_muhib.classmate.core.Constants.USERS_COLLECTION
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import javax.inject.Inject
import kotlin.Exception

class UserRepositoryImpl @Inject constructor(
    firestoreRef: FirebaseFirestore
) : UserRepository {

    private val usersCollection = firestoreRef.collection(USERS_COLLECTION)

    override fun updateUser(email: String, user: User): Flow<ResponseState<User>> = callbackFlow {
        var isSuccessful = false
        var response: ResponseState<User> = ResponseState.Loading

        try {

            usersCollection.document(email).get().addOnSuccessListener {
                isSuccessful = true
                val userDocument = usersCollection.document(email)

                if (user.firstName.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(FIRST_NAME to user.firstName))
                        .addOnSuccessListener {
                            isSuccessful = true
                        }
                }

                if (user.lastName.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(LAST_NAME to user.lastName))
                        .addOnSuccessListener {
                            isSuccessful = true
                        }
                }

                if (user.role.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(ROLE to user.role)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if (user.department.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(DEPARTMENT to user.department))
                        .addOnSuccessListener {
                            isSuccessful = true
                        }
                }

                if (user.session.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(SESSION to user.session)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if (user.phoneNo.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(PHONE_NO to user.phoneNo)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if (user.bloodGroup.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(BLOOD_GROUP to user.bloodGroup))
                        .addOnSuccessListener {
                            isSuccessful = true
                        }
                }

//                    if (user.enrolledCourse.isNotEmpty()) {
//                        isSuccessful = false
//                        userDocument.update(user.toEnrolledMap()).addOnSuccessListener {
//                            isSuccessful = true
//                        }
//                    }
//
//                    if (user.requestedCourse.isNotEmpty()) {
//                        userDocument.update(user.toMapRequested()).addOnSuccessListener {
//                            isSuccessful = true
//                        }
//                    }

            }

            if (isSuccessful) {
                response = ResponseState.Success(user)
            }

        } catch (e: Exception) {
            response = ResponseState.Failure(e.message.toString())
            Log.d(TAG, "updateUser: ${e.localizedMessage}")
        }

        trySend(response)

        awaitClose {

        }
    }


    override fun getUser(email: String): Flow<ResponseState<User>> = callbackFlow {
        var response: ResponseState<User> = ResponseState.Loading

        val snapshotListener =
            usersCollection.document(email).addSnapshotListener { snapshot, error ->
                if (snapshot != null) {
                    val firstName: String = snapshot[FIRST_NAME].toString()
                    val lastName: String = snapshot[LAST_NAME].toString()
                    val role: String = snapshot[ROLE].toString()
                    val department: String = snapshot[DEPARTMENT].toString()
                    val session: String = snapshot[SESSION].toString()
                    val bloodGroup: String = snapshot[BLOOD_GROUP].toString()
                    val phoneNo: String = snapshot[PHONE_NO].toString()
                    val userEmail: String = snapshot[EMAIL].toString()
                    val userPassword: String = snapshot[PASSWORD].toString()
                    val enrolledCourses: List<String> = listOf(snapshot[ENROLLED].toString())
                    val requestedCourses: List<String> = listOf(snapshot[REQUESTED].toString())

                    Log.d(
                        TAG,
                        "variables: $firstName, $lastName, $role, $department, $bloodGroup, $phoneNo, $userEmail, $userPassword"
                    )

                    val user = User(
                        firstName = firstName,
                        lastName = lastName,
                        role = role,
                        department = department,
                        session = session,
                        bloodGroup = bloodGroup,
                        phoneNo = phoneNo,
                        email = userEmail,
                        password = userPassword
                    )
                    response = ResponseState.Success(user)
                } else {
                    response = ResponseState.Failure(error?.message.toString())
                }

                Log.d(TAG, "Snapshot: $snapshot")

                trySend(response).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

}
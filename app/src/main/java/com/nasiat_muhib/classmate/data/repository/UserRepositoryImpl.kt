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
import com.nasiat_muhib.classmate.core.Constants.USER_DOES_NOT_EXIST
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

        var response: ResponseState<User> = ResponseState.Loading
        var isSuccessful = false

        usersCollection.document(email).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                isSuccessful = true
                val userDocument = usersCollection.document(email)
                if (user.firstName.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(FIRST_NAME to user.firstName)).addOnSuccessListener {
                        isSuccessful = true
                    }
                    Log.d(TAG, "firstname Update: $isSuccessful")
                }

                if (user.lastName.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(LAST_NAME to user.lastName)).addOnSuccessListener {
                        isSuccessful = true
                    }
                    Log.d(TAG, "lastname Update: $isSuccessful")
                }

//                if(user.role.isNotEmpty()) {
//                    isSuccessful = false
//                    userDocument.update(mapOf(ROLE to user.role)).addOnSuccessListener {
//                        isSuccessful = true
//                    }
//                }

                if (user.department.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(DEPARTMENT to user.department)).addOnSuccessListener {
                        isSuccessful = true
                    }
                    Log.d(TAG, "department Update: $isSuccessful")
                }

                if (user.session.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(SESSION to user.session)).addOnSuccessListener {
                        isSuccessful = true
                    }
                    Log.d(TAG, "session Update: $isSuccessful")
                }

                if (user.bloodGroup.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(BLOOD_GROUP to user.bloodGroup))
                        .addOnSuccessListener {
                            isSuccessful = true
                        }
                    Log.d(TAG, "bloodGroup Update: $isSuccessful")
                }

                if (user.phoneNo.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(PHONE_NO to user.phoneNo)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

            } else {
                response = ResponseState.Failure(USER_DOES_NOT_EXIST)
            }

        }.addOnFailureListener { e ->
            response = ResponseState.Failure(e.message.toString())
        }.await()

        if (isSuccessful) {
            response = ResponseState.Success(user)
        }

        trySend(response).isSuccess

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
                    val enrolledCourses: List<String> = snapshot[ENROLLED] as List<String>
                    val requestedCourses: List<String> = snapshot[REQUESTED] as List<String>

//                    Log.d(
//                        TAG,
//                        "variables: $firstName, $lastName, $role, $department, $bloodGroup, $phoneNo, $userEmail, $userPassword"
//                    )

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
                } else if (error != null) {
                    response = ResponseState.Failure(error.message.toString())
                }

//                Log.d(TAG, "Snapshot: $snapshot")

                trySend(response).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

}
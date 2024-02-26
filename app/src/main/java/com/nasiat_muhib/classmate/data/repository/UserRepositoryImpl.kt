package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.Constants.BLOOD_GROUP
import com.nasiat_muhib.classmate.core.Constants.CREATED_COURSE
import com.nasiat_muhib.classmate.core.Constants.DEPARTMENT
import com.nasiat_muhib.classmate.core.Constants.ENROLLED_COURSE
import com.nasiat_muhib.classmate.core.Constants.FIRST_NAME
import com.nasiat_muhib.classmate.core.Constants.LAST_NAME
import com.nasiat_muhib.classmate.core.Constants.PHONE_NO
import com.nasiat_muhib.classmate.core.Constants.REQUESTED_COURSE
import com.nasiat_muhib.classmate.core.Constants.ROLE
import com.nasiat_muhib.classmate.core.Constants.SESSION
import com.nasiat_muhib.classmate.core.Constants.TAG
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_GET_USER
import com.nasiat_muhib.classmate.core.Constants.USERS_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.USER_DOES_NOT_EXIST
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
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

                if(user.role.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(ROLE to user.role)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

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

                if (user.enrolledCourse.isNotEmpty()) {

                    val enrolled: List<String> = if(snapshot[ENROLLED_COURSE] is List<*> && snapshot[ENROLLED_COURSE] != null) snapshot[ENROLLED_COURSE] as List<String> else mutableListOf()
                    val mutableList = mutableListOf<String>()

                    enrolled.forEach {
                        mutableList.add(it)
                    }

                    enrolled.forEach { enroll ->
                        user.enrolledCourse.forEach {
                            if(it == enroll) {
                                mutableList.remove(it)
                            }
                        }
                    }

                    user.enrolledCourse.forEach {
                        mutableList.add(it)
                    }

                    isSuccessful = false
                    userDocument.update(mapOf(ENROLLED_COURSE to mutableList)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if (user.requestedCourse.isNotEmpty()) {

                    val requested: List<String> = if(snapshot[REQUESTED_COURSE] is List<*> && snapshot[REQUESTED_COURSE] != null) snapshot[REQUESTED_COURSE] as List<String> else mutableListOf()
                    val mutableList = mutableListOf<String>()

                    requested.forEach {
                        mutableList.add(it)
                    }

                    requested.forEach { request ->
                            user.requestedCourse.forEach {
                            if(it == request) {
                                mutableList.remove(it)
                            }
                        }
                    }

                    user.requestedCourse.forEach {
                        mutableList.add(it)
                    }

                    isSuccessful = false
                    userDocument.update(mapOf(REQUESTED_COURSE to mutableList)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if (user.createdCourse.isNotEmpty()) {

                    val created: List<String> = if(snapshot[CREATED_COURSE] is List<*> && snapshot[CREATED_COURSE] != null) snapshot[CREATED_COURSE] as List<String> else mutableListOf()
                    val mutableList = mutableListOf<String>()

                    created.forEach {
                        mutableList.add(it)
                    }

                    created.forEach { create ->
                        user.createdCourse.forEach {
                            if(it == create) {
                                mutableList.remove(it)
                            }
                        }
                    }

                    user.createdCourse.forEach {
                        mutableList.add(it)
                    }

                    isSuccessful = false
                    userDocument.update(mapOf(CREATED_COURSE to mutableList)).addOnSuccessListener {
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
                    response = try {
                        val user = DocumentSnapshotToObjectFunctions.getUserFromDocumentSnapshot(snapshot)
                        ResponseState.Success(user)
                    } catch (e: Exception) {
                        ResponseState.Failure("$UNABLE_TO_GET_USER: ${e.localizedMessage}")
                    }

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
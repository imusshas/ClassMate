package com.nasiat_muhib.classmate.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.Constants.BLOOD_GROUP
import com.nasiat_muhib.classmate.core.Constants.COURSES_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.DEPARTMENT
import com.nasiat_muhib.classmate.core.Constants.FIRST_NAME
import com.nasiat_muhib.classmate.core.Constants.LAST_NAME
import com.nasiat_muhib.classmate.core.Constants.PHONE_NO
import com.nasiat_muhib.classmate.core.Constants.ROLE
import com.nasiat_muhib.classmate.core.Constants.SESSION
import com.nasiat_muhib.classmate.core.Constants.USERS_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.USER_DOES_NOT_EXIST
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val usersCollection: CollectionReference
): UserRepository {
    override suspend fun createUser(user: User): Flow<ResponseState<User>> = flow {
        var isSuccessful = false
        emit(ResponseState.Loading)
        try {
            usersCollection.document(user.email).set(user.toMap()).addOnSuccessListener {
                isSuccessful = true
            }.await()
            if (isSuccessful) {
                emit(ResponseState.Success(user))
            }
        }catch (e: Exception) {
            emit(ResponseState.Failure(e.message.toString()))
        }
    }

    override suspend fun updateUser(email: String, user: User): Flow<ResponseState<User>> = flow {
        var isSuccessful = false
        emit(ResponseState.Loading)
        try {

            usersCollection.document(user.email).get().addOnSuccessListener {
                isSuccessful = true

                val userDocument = usersCollection.document(user.email)

                if(user.firstName.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(FIRST_NAME to user.firstName)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if(user.lastName.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(LAST_NAME to user.lastName)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if(user.role.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(ROLE to user.role)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if(user.department.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(DEPARTMENT to user.department)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if(user.session.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(SESSION to user.session)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if(user.phoneNo.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(PHONE_NO to user.phoneNo)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if(user.bloodGroup.isNotEmpty()) {
                    isSuccessful = false
                    userDocument.update(mapOf(BLOOD_GROUP to user.bloodGroup)).addOnSuccessListener {
                        isSuccessful = true
                    }
                }

                if (user.enrolledCourse.isNotEmpty()) {
                    isSuccessful = false
                    user.enrolledCourse.forEachIndexed { index, course ->
                        userDocument.collection(COURSES_COLLECTION).document(course.code).update(user.toMapEnrolled()).addOnSuccessListener {
                            isSuccessful = true
                        }
                    }
                }
            }.await()
            if (isSuccessful) {
                emit(ResponseState.Success(user))
            }
        }catch (e: Exception) {
            emit(ResponseState.Failure(e.message.toString()))
        }
    }

    override suspend fun getUser(email: String): Flow<ResponseState<User>> = flow {
        emit(ResponseState.Loading)
        var user: User? = User()
        try {
            if(email == auth.currentUser?.email) {
                usersCollection.document(email).get().addOnSuccessListener { userDocumnet ->
                    user = userDocumnet.toObject<User>(User::class.java)
                }.await()
                emit(ResponseState.Success(user))
            } else {
                emit(ResponseState.Failure(USER_DOES_NOT_EXIST))
            }
        } catch (e: Exception) {
            emit(ResponseState.Failure(e.message.toString()))
        }
    }

}
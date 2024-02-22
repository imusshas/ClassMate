package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.nasiat_muhib.classmate.core.Constants.TAG
import kotlin.Exception


class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val usersCollection: CollectionReference,
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun signUp(
        email: String,
        password: String,
        user: User
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        var isSuccessful = false
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                if (it != null) {
                    isSuccessful = true
                    Log.d(TAG, "signUp: Successful")
                    isSuccessful = createUser(user)
                } else {
                    Log.d(TAG, "signUp: Failed")
                }
            }.await()

            if (isSuccessful) {
                emit(ResponseState.Success(isSuccessful))
            }

        } catch (e: Exception) {
            emit(ResponseState.Failure(e.message.toString()))
        }
    }

    override fun signIn(email: String, password: String): Flow<ResponseState<Boolean>> =
        flow {
            emit(ResponseState.Loading)
            var isSuccessful = false
            try {
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    if (it != null) {
                        isSuccessful = true
                    }
                }.await()
                if (isSuccessful) {
                    emit(ResponseState.Success(isSuccessful))
                }
            } catch (e: Exception) {
                emit(ResponseState.Failure(e.message.toString()))
            }
        }

    override fun signOut(): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            auth.signOut()
            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Failure(e.message.toString()))
        }
    }

    private fun createUser(user: User): Boolean {
        var isSuccessful = false
        try {
            if(auth.currentUser?.uid != null) {
                user.userId = auth.currentUser?.uid!!
                usersCollection.document(user.email).set(user.toMap()).addOnCompleteListener {
                    if(it.isSuccessful) {
                        isSuccessful = true
                        Log.d(TAG, "createUser: Successful")
                    }
                }
            } else {
                isSuccessful = false
                Log.d(TAG, "createUser: Failed")
            }
        } catch (e: Exception) {
            Log.d(TAG, "createUser: ${e.message}")
            isSuccessful = false
        }
        return isSuccessful
    }
}
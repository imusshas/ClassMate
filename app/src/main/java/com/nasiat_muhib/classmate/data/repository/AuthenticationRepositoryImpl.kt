package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.USERS_COLLECTION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestoreRef: FirebaseFirestore,
) : AuthenticationRepository {

    override fun signIn(email: String, password: String): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)
            emit(DataState.Success(signInToFirebase(email, password)))
        }.catch {
            emit(DataState.Error("Unable To Sign In"))
            Log.d(TAG, "signIn: ${it.localizedMessage}")
        }

    override fun signUp(email: String, password: String, user: User): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)

            if (signUpToFirebase(email, password)) {
                createUserToFirebase(email, user)
                emit(DataState.Success(true))
            }

        }.catch {
            emit(DataState.Error("Unable To Sign Up"))
            Log.d(TAG, "signUp: ${it.localizedMessage}")
        }

    override fun signOut(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            auth.signOut()
            Log.d(TAG, "signOut: ${auth.currentUser}")
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Error(e.message.toString()))
        }
        emit(DataState.Success(true))
    }.catch {
        emit(DataState.Error("Unable To Sign Out"))
        Log.d(TAG, "signOut: ${it.localizedMessage}")
    }

    override fun resetPassword(email: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)

        val response = sendResetPasswordLinkFromFirebase(email)
        Log.d(TAG, "resetPassword: response: $response")

        if (response) {
            emit(DataState.Success(true))
        } else {
            emit(DataState.Error("Unable to send email."))
        }
    }.catch {
        emit(DataState.Error("Unable to send email."))
        Log.d(TAG, "resetPassword: ${it.localizedMessage}")
    }


    private suspend fun signInToFirebase(email: String, password: String): Boolean {
        var isSuccessful = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful && it.result != null) {
                    isSuccessful = it.isSuccessful
                    Log.d(TAG, "signInToFirebase: ${it.result.user?.email}")
                }
            }.addOnFailureListener {
                Log.d(TAG, "signInToFirebase: ${it.localizedMessage}")
            }.await()

        return isSuccessful

    }

    private suspend fun signUpToFirebase(email: String, password: String): Boolean {
        var isSuccessful = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isSuccessful = true
                }
            }.addOnFailureListener {
                Log.d(TAG, "signUpToFirebase: ${it.localizedMessage}")
            }.await()
        return isSuccessful

    }

    private suspend fun createUserToFirebase(email: String, user: User): Boolean {

        var isSuccessful = false
        firestoreRef.collection(USERS_COLLECTION).document(email)
            .set(user.toMap())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isSuccessful = true
                }
            }.addOnFailureListener {
                Log.d(TAG, "createUserToFirebase: ${it.localizedMessage}")
            }.await()

        return isSuccessful
    }

    private suspend fun sendResetPasswordLinkFromFirebase(email: String): Boolean {
        var isSuccessful = false

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                isSuccessful = it.isSuccessful
                Log.d(TAG, "sendResetPasswordLinkFromFirebase: ${it.isSuccessful}")
            }.addOnFailureListener {
                Log.d(TAG, "sendResetPasswordLinkFromFirebase: ${it.localizedMessage}")
            }.await()

        return isSuccessful
    }

    companion object {
        private const val TAG = "AuthenticationRepositoryImpl"
    }
}
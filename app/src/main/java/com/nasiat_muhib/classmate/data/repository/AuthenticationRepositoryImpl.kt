package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.USERS_COLLECTION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestoreRef: FirebaseFirestore,
) : AuthenticationRepository {

    private val usersCollection = firestoreRef.collection(USERS_COLLECTION)

    override fun signIn(email: String, password: String): Flow<DataState<FirebaseUser?>> =
        flow {
            emit(DataState.Loading)
            val authResult = auth.signInWithEmailAndPassword(email, password).await()

            if (authResult.user != null && authResult.user?.email != null) {
                emit(DataState.Success(authResult.user))
            }
        }
            .catch {
                emit(DataState.Error("Unable To Sign In"))
                Log.d(TAG, "signIn: ${it.localizedMessage}")
            }

    override fun signUp(email: String, password: String, user: User): Flow<DataState<FirebaseUser?>> =
        flow {
            emit(DataState.Loading)

            val authResult = auth.createUserWithEmailAndPassword(email, password).await()

            if (authResult.user != null && authResult.user?.email != null) {
                usersCollection.document(email).set(user.toMap()).await()
                emit(DataState.Success(authResult.user))
            } else {
                emit(DataState.Error("Unable To Create User"))
            }

        }.catch {
            if (it.localizedMessage == "The email address is already in use by another account.") {
                emit(DataState.Error("Account Already Exists"))
            } else {
                emit(DataState.Error("Unable To Sign Up"))
            }
            Log.d(TAG, "signUp: ${it.localizedMessage}")
        }

    override fun signOut(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        auth.signOut()
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
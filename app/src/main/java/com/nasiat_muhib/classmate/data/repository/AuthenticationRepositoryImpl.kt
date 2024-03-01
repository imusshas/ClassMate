package com.nasiat_muhib.classmate.data.repository

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
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

    override fun signIn(email: String, password: String): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)

            val response = signInToFirebase(email, password)
            if (response) {
                emit(DataState.Success(true))
            } else {
                emit(DataState.Error("Unable To Sign In"))
            }

        }.catch {
            emit(DataState.Error("Unable To Sign In"))
            Log.d(TAG, "signIn: ${it.localizedMessage}")
        }

    override fun signUp(email: String, password: String, user: User): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)

            var response = createUserToFirebase(email, user)
            Log.d(TAG, "signUp: response for create user: $response")
            if (response) {
                response = signUpToFirebase(email, password)
                if (response) {
                    emit(DataState.Success(true))
                } else {
                    emit(DataState.Error("Unable To Sign Up"))
                }
            } else {
                emit(DataState.Error("Unable To Create User"))
            }

        }.catch {
            emit(DataState.Error("Unable To Sign Up"))
            Log.d(TAG, "signUp: ${it.localizedMessage}")
        }

    override fun signOut(): Flow<DataState<Boolean>> = flow {

        val response = signOutFromFirebase()

        if (response) {
            emit(DataState.Success(true))
        } else {
            emit(DataState.Error("Unable To Sign Out"))
        }
    }.catch {
        emit(DataState.Error("Unable To Sign Out"))
        Log.d(TAG, "signOut: ${it.localizedMessage}")
    }

    override fun resetPassword(): Flow<DataState<Boolean>> {
        TODO("Not yet implemented")
    }


    private suspend fun signInToFirebase(email: String, password: String): Boolean {
        var isSuccessful = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.addOnFailureListener {
                Log.d(TAG, "signInToFirebase: ${it.localizedMessage}")
            }.await()

        return isSuccessful

    }

    private suspend fun signUpToFirebase(email: String, password: String): Boolean {
        var isSuccessful = false

//        Log.d(TAG, "signUpToFirebase: calling function")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isSuccessful =  true
                }
//                Log.d(TAG, "signUpToFirebase: ${it.isSuccessful}")
            }.addOnFailureListener {
                Log.d(TAG, "signUpToFirebase: ${it.localizedMessage}")
            }.await()

        return isSuccessful

    }

    private suspend fun createUserToFirebase(email: String, user: User): Boolean {

        Log.d(TAG, "createUserToFirebase: $user")
        var isSuccessful = false
        firestoreRef.collection(USERS_COLLECTION).document(email)
            .set(user.toMap())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isSuccessful = true
//                    Log.d(TAG, "createUserToFirebase: isSuccessful inside: $isSuccessful")
                }
//                Log.d(TAG, "createUserToFirebase: ${it.isSuccessful}")
            }.addOnFailureListener {
                Log.d(TAG, "createUserToFirebase: ${it.localizedMessage}")
            }.await()

        Log.d(TAG, "createUserToFirebase: isSuccessful outside: $isSuccessful, email: $email")

        return isSuccessful
    }

    private fun signOutFromFirebase(): Boolean {

        var isSuccessful = false

        auth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (auth.currentUser == null) {
                ClassMateAppRouter.navigateTo(Screen.SignInScreen)
                isSuccessful = true
            }
        }

        auth.addAuthStateListener(authStateListener)

        return isSuccessful
    }

    companion object {
        const val TAG = "AuthenticationRepositoryImpl"
    }
}
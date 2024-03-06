package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getUserFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestoreRef: FirebaseFirestore,
) : UserRepository {

    private val usersCollection = firestoreRef.collection(USERS_COLLECTION)
    override val currentUser: FirebaseUser
        get() = auth.currentUser!!


    override fun getUser(email: String): Flow<DataState<User>> = callbackFlow {
        var response: DataState<User> = DataState.Loading

        val snapshotListener =  usersCollection.document(email)
            .addSnapshotListener { value, error ->
                if(value != null) {
                    Log.d(TAG, "getUser: Document: ${value.data}")
                    val user = getUserFromFirestoreDocument(value)
                    Log.d(TAG, "getUser: User: $user")
                    response = DataState.Success(user)
                } else if (error != null) {
                    Log.d(TAG, "getUser: ${error.localizedMessage}")
                    response = DataState.Error(error.localizedMessage!!)
                } else {
                    Log.d(TAG, "getUser: Nothing")
                }

                trySend(response).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }
    }


    override fun updateUser(email: String, user: User): Flow<DataState<User>> = callbackFlow {

        var response: DataState<User> = DataState.Loading

        val isSuccessful = updateUserToFirestore(email, user)

        response = if (isSuccessful) {
            DataState.Success(user)
        } else {
            DataState.Error("Unable to update user")
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }


    private suspend fun updateUserToFirestore(email: String, user: User): Boolean {

        var isSuccessful = false

        usersCollection.document(email).get().addOnSuccessListener { userSnapshot ->
            val document = usersCollection.document(email)
            if (userSnapshot.exists()) {
                document.update(user.toMap()).addOnCompleteListener {
                    isSuccessful = it.isSuccessful
                }
            } else {
                Log.d(TAG, "updateUserToFirestore: User doesn't exist")
            }
        }.addOnFailureListener {
            Log.d(TAG, "updateUserToFirestore: ${it.localizedMessage}")
        }.await()
        Log.d(TAG, "updateUserToFirestore: isSuccessful Outside: $isSuccessful")
        return isSuccessful
    }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}




//callbackFlow {
//    var response: DataState<User> = DataState.Loading
//
//            val snapshotListener =  usersCollection.document(email)
//                .addSnapshotListener { value, error ->
//                if(value != null) {
//                    Log.d(TAG, "getUser: Document: ${value.data}")
//                    val user = getUserFromFirestoreDocument(value)
//                    Log.d(TAG, "getUser: User: $user")
//                    response = DataState.Success(user)
//                } else if (error != null) {
//                    Log.d(TAG, "getUser: ${error.localizedMessage}")
//                    response = DataState.Error(error.localizedMessage!!)
//                } else {
//                    Log.d(TAG, "getUser: Nothing")
//                }
//            }
//
//    trySend(response).isSuccess
//
//    awaitClose {
//                snapshotListener.remove()
//    }
//}










//flow {
//    emit(DataState.Loading)
//    var data = User()
//    var error: String? = null
//
//    usersCollection.document(email).get()
//        .addOnSuccessListener {
//            data = getUserFromFirestoreDocument(it)
//            Log.d(UserRepositoryImpl.TAG, "getUser: ${it.data}")
//        }.addOnFailureListener {
//            error = it.localizedMessage
//            Log.d(UserRepositoryImpl.TAG, "getUser: ${it.localizedMessage}")
//        }.await()
//
//    if (error == null) {
//        emit(DataState.Success(data))
//    } else {
//        emit(DataState.Error(error!!))
//    }
//}
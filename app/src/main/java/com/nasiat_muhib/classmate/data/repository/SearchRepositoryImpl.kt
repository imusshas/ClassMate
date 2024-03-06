package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getUserFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.repository.SearchRepository
import com.nasiat_muhib.classmate.strings.TEACHER
import com.nasiat_muhib.classmate.strings.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore
): SearchRepository {
    override fun getAllTeachers(): Flow<Set<User>> = callbackFlow {



        val teachers = mutableSetOf<User>()

        val snapshotListener = firestoreRef.collection(USERS_COLLECTION)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    value.documents.forEach {
                        val user = getUserFromFirestoreDocument(it)

                        if (user.role == TEACHER) {
                            teachers.add(user)
                        }
                    }
                } else if(error != null) {
                    Log.d(TAG, "getAllTeachers: $error")
                }

                trySend(teachers).isSuccess
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getAllCourses(): Flow<Set<User>> {
        TODO("Not yet implemented")
    }


    companion object {
        const val TAG = "SearchRepositoryImpl"
    }
}
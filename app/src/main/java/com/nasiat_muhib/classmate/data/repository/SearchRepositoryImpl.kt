package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getCourseFromFirestoreDocument
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getUserFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.repository.SearchRepository
import com.nasiat_muhib.classmate.strings.COURSES_COLLECTION
import com.nasiat_muhib.classmate.strings.TEACHER
import com.nasiat_muhib.classmate.strings.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
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
    }.catch {
        Log.d(TAG, "getAllTeachers: $it")
    }

    
    
    
    
    override fun getAllCourses(): Flow<Set<Course>> = callbackFlow{
        val courses = mutableSetOf<Course>()
        
        val snapshotListener = firestoreRef.collection(COURSES_COLLECTION)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    value.documents.forEach {
                        val course = getCourseFromFirestoreDocument(it)

                        if (!course.pendingStatus) {
                            courses.add(course)
                        }
                    }
                } else if(error != null) {
                    Log.d(TAG, "getAllTeachers: $error")
                }

                trySend(courses).isSuccess
            }
        
        awaitClose { 
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getAllCourses: $it")
    }


    companion object {
        const val TAG = "SearchRepositoryImpl"
    }
}
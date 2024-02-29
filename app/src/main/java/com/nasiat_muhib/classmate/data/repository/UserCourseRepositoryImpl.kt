package com.nasiat_muhib.classmate.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.nasiat_muhib.classmate.core.Constants.COURSES_COLLECTION
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions.getCourseFromDocumentSnapshot
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.model.DataOrException
import com.nasiat_muhib.classmate.domain.repository.UserCourseRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserCourseRepositoryImpl @Inject constructor(
    firestoreRef: FirebaseFirestore
): UserCourseRepository {

    private val coursesCollection = firestoreRef.collection(COURSES_COLLECTION)
    override fun getUserCourseList(coursesList: List<String>): Flow<DataOrException<List<Course>>> = callbackFlow {

        val mutableList = mutableListOf<Course>()
        var errorMessage = ""

        var snapshotListener: ListenerRegistration = ListenerRegistration {  }

        coursesList.forEach {
            snapshotListener = coursesCollection.document(it)
                .addSnapshotListener { value, error ->
                if(value != null) {
                    val course = getCourseFromDocumentSnapshot(value)
                    mutableList.add(course)
                } else if(error != null) {
                    errorMessage = error.localizedMessage?.toString() ?: ""
                }
            }
        }

        val response = if(errorMessage.isEmpty()) DataOrException.Data(mutableList) else DataOrException.Exception(errorMessage)

        trySend(response).isSuccess

        awaitClose {
            snapshotListener.remove()
        }
    }
}
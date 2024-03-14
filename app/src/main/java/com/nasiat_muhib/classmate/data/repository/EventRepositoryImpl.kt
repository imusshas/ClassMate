package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.data.model.Event
import com.nasiat_muhib.classmate.domain.repository.EventRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.ASSIGNMENTS_COLLECTION
import com.nasiat_muhib.classmate.strings.TERM_TESTS_COLLECTION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore
): EventRepository {

    private val termTestCollection = firestoreRef.collection(TERM_TESTS_COLLECTION)
    private val assignmentCollection = firestoreRef.collection(ASSIGNMENTS_COLLECTION)
    override fun createTermTest(event: Event): Flow<DataState<Event>> = flow {

        emit(DataState.Loading)

        val eventId = "${event.department}:${event.courseCode}:${event.eventNo}"

        termTestCollection.document(eventId).set(event.toMap()).await()

        emit(DataState.Success(event))
    }.catch {
        Log.d(TAG, "createTermTest: ${it.localizedMessage}")
    }

    override fun getTermTestList(courseId: String): Flow<List<Event>>  = callbackFlow{

        val snapshotListener = termTestCollection.addSnapshotListener { value, error ->

        }
    }

    override fun deleteTermTest(event: Event): Flow<DataState<Event>> {
        TODO("Not yet implemented")
    }

    override fun createAssignment(event: Event): Flow<DataState<Event>> {
        TODO("Not yet implemented")
    }

    override fun getAssignmentList(courseId: String): Flow<List<Event>> {
        TODO("Not yet implemented")
    }

    override fun deleteAssignment(event: Event): Flow<DataState<Event>> {
        TODO("Not yet implemented")
    }


    companion object {
        const val TAG = "EventRepositoryImpl"
    }

}
package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getClassDetailsFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.repository.ClassDetailsRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.CLASSES_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClassDetailsRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore
): ClassDetailsRepository{

    private val classesCollection = firestoreRef.collection(CLASSES_COLLECTION)

    override fun createClass(classDetails: ClassDetails): Flow<DataState<ClassDetails>> = flow{
        emit(DataState.Loading)
        val classId = "${classDetails.classDepartment}:${classDetails.classCourseCode}:${classDetails.classNo}"
        classesCollection.document(classId).set(classDetails.toMap()).await()
        emit(DataState.Success(classDetails))
    }.catch {
        Log.d(TAG, "createClass: ${it.message}")
    }

    override fun deleteClass(classDetails: ClassDetails): Flow<DataState<ClassDetails>> = flow{
        emit(DataState.Loading)
        val classId = "${classDetails.classDepartment}:${classDetails.classCourseCode}:${classDetails.classNo}"
        classesCollection.document(classId).delete().await()
        emit(DataState.Success(classDetails))
    }.catch {
        Log.d(TAG, "deleteClass: ${it.localizedMessage}")
    }

    override fun getClasses(courseId: String): Flow<List<ClassDetails>> = callbackFlow {

        val snapshotListener = classesCollection.addSnapshotListener { value, error ->
            val classList = mutableListOf<ClassDetails>()
            value?.documents?.forEach {
                if (it.exists() && it != null && it.id.contains(courseId)) {
                    val details = getClassDetailsFromFirestoreDocument(it)
                    classList.add(details)
                }
            }

            trySend(classList).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    companion object {
        const val TAG = "ClassDetailsRepositoryImpl"
    }
}
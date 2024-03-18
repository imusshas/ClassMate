package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getResourceLinkFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.ResourceLink
import com.nasiat_muhib.classmate.domain.repository.ResourceLinkRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.RESOURCES_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ResourceLinkRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore
): ResourceLinkRepository {

    private val resourcesCollection = firestoreRef.collection(RESOURCES_COLLECTION)
    override fun createResource(resourceLink: ResourceLink): Flow<DataState<ResourceLink>> = flow {
        emit(DataState.Loading)
        val resourceId = "${resourceLink.resourceDepartment}:${resourceLink.resourceCourseCode}:${resourceLink.resourceNo}"
        resourcesCollection.document(resourceId).set(resourceLink.toMap()).await()
        emit(DataState.Success(resourceLink))
    }.catch {
        Log.d(TAG, "createResource: ${it.localizedMessage}")
    }

    override fun getResources(courseId: String): Flow<List<ResourceLink>> = callbackFlow {
        val snapshotListener = resourcesCollection.addSnapshotListener { value, error ->
            val resourceSet = mutableSetOf<ResourceLink>()
            value?.documents?.forEach {
                if (it.id.contains(courseId)) {
                    val resource = getResourceLinkFromFirestoreDocument(it)
                    resourceSet.add(resource)
                }
            }

            trySend(resourceSet.toList()).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }.catch {
        Log.d(TAG, "getResources: ${it.localizedMessage}")
    }

    override fun deleteResource(resourceLink: ResourceLink): Flow<DataState<ResourceLink>> = flow {
        emit(DataState.Loading)
        val resourceId = "${resourceLink.resourceDepartment}:${resourceLink.resourceCourseCode}:${resourceLink.resourceNo}"
        resourcesCollection.document(resourceId).delete().await()
        emit(DataState.Success(resourceLink))
    }.catch {
        Log.d(TAG, "deleteResource: ${it.localizedMessage}")
    }

    companion object {
        const val TAG = "ResourceLinkRepositoryImpl"
    }
}
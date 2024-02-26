package com.nasiat_muhib.classmate.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.core.Constants.CLASS_DETAILS_ALREADY_EXISTS
import com.nasiat_muhib.classmate.core.Constants.CLASS_DETAILS_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.CLASS_DETAILS_DOES_NOT_EXIST
import com.nasiat_muhib.classmate.core.Constants.COURSES_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.CREATOR
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_CREATE_CLASS_DETAILS
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_GET_CLASS_DETAILS
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_GET_COURSE
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_UPDATE_CLASS_DETAILS
import com.nasiat_muhib.classmate.core.Constants.YOU_ARE_NOT_THE_COURSE_CREATOR
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions.getClassDetailsFromDocumentSnapshot
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions.getMapFromClassDetails
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.ClassDetailsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ClassDetailsRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestoreRef: FirebaseFirestore
): ClassDetailsRepository {
    override fun createClassDetailsList(courseCode: String, classDetailsList: List<ClassDetails>): Flow<ResponseState<List<ClassDetails>>> = callbackFlow {
        var response: ResponseState<List<ClassDetails>> = ResponseState.Loading

        firestoreRef.collection(COURSES_COLLECTION).document(courseCode).get().addOnSuccessListener { courseSnapshot ->
            val courseCreator = courseSnapshot[CREATOR] as String
            val currentUser = auth.currentUser?.email

            if(courseCreator != currentUser) {
                response = ResponseState.Failure(YOU_ARE_NOT_THE_COURSE_CREATOR)
            } else {
                firestoreRef.collection(CLASS_DETAILS_COLLECTION).document(courseCode).get().addOnSuccessListener { classDetailsSnapshot ->
                    if(classDetailsSnapshot.exists()) {
                        response = ResponseState.Failure(CLASS_DETAILS_ALREADY_EXISTS)
                    } else {
                        firestoreRef.collection(CLASS_DETAILS_COLLECTION).document(courseCode).set(getMapFromClassDetails(classDetailsList))
                            .addOnSuccessListener {
                                response = ResponseState.Success(classDetailsList)
                            }.addOnFailureListener {
                                response = ResponseState.Failure("$UNABLE_TO_CREATE_CLASS_DETAILS: ${it.localizedMessage}")
                            }
                    }
                }.addOnFailureListener {
                    response = ResponseState.Failure("$UNABLE_TO_CREATE_CLASS_DETAILS: ${it.localizedMessage}")
                }
            }
        }.addOnFailureListener {
            response = ResponseState.Failure("$UNABLE_TO_GET_COURSE: ${it.localizedMessage}")
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    override fun updateClassDetailsList(courseCode: String, classDetailsList: List<ClassDetails>): Flow<ResponseState<List<ClassDetails>>> = callbackFlow {
        var response: ResponseState<List<ClassDetails>> = ResponseState.Loading

        firestoreRef.collection(COURSES_COLLECTION).document(courseCode).get().addOnSuccessListener { courseSnapshot ->
            val courseCreator = courseSnapshot[CREATOR] as String
            val currentUser = auth.currentUser?.email

            if(courseCreator != currentUser) {
                response = ResponseState.Failure(YOU_ARE_NOT_THE_COURSE_CREATOR)
            } else {
                firestoreRef.collection(CLASS_DETAILS_COLLECTION).document(courseCode).get().addOnSuccessListener { classDetailsSnapshot ->
                    if(classDetailsSnapshot.exists()) {
                        val details = getClassDetailsFromDocumentSnapshot(classDetailsSnapshot)
                        val mutableDetails = mutableListOf<ClassDetails>()
                        details.forEach {
                            mutableDetails.add(it)
                        }

                        details.forEach { detail ->
                            classDetailsList.forEach {
                                if(it.weekDay == detail.weekDay && it.time == detail.time) {
                                    mutableDetails.remove(detail)
                                }
                            }
                        }

                        classDetailsList.forEach {
                            mutableDetails.add(it)
                        }

                        firestoreRef.collection(CLASS_DETAILS_COLLECTION).document(courseCode).update(getMapFromClassDetails(mutableDetails))
                            .addOnSuccessListener {
                                response = ResponseState.Success(classDetailsList)
                            }.addOnFailureListener {
                                response = ResponseState.Failure("$UNABLE_TO_UPDATE_CLASS_DETAILS: ${it.localizedMessage}")
                            }
                    } else {
                        response = ResponseState.Failure(CLASS_DETAILS_DOES_NOT_EXIST)
                    }
                }.addOnFailureListener {
                    response = ResponseState.Failure("$UNABLE_TO_UPDATE_CLASS_DETAILS: ${it.localizedMessage}")
                }
            }
        }.addOnFailureListener {
            response = ResponseState.Failure("$UNABLE_TO_GET_CLASS_DETAILS: ${it.localizedMessage}")
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    override fun deleteClassDetailsList(courseCode: String): Flow<ResponseState<List<ClassDetails>>> {
        TODO("Not yet implemented")
    }

    override fun getClassDetailsList(courseCode: String): Flow<ResponseState<List<ClassDetails>>> = callbackFlow {
        var response: ResponseState<List<ClassDetails>> = ResponseState.Loading

        val snapshotListener =
            firestoreRef.collection(CLASS_DETAILS_COLLECTION).document(courseCode)
                .addSnapshotListener { classDetailsDocument, error ->
                    response = if (classDetailsDocument != null) {
                        val classDetailsList =
                            getClassDetailsFromDocumentSnapshot(classDetailsDocument)
                        ResponseState.Success(classDetailsList)
                    } else {
                        ResponseState.Failure("${Constants.UNABLE_TO_GET_CLASS_DETAILS} :${error?.localizedMessage}")
                    }
                }

        trySend(response).isSuccess

        awaitClose {
            snapshotListener.remove()
        }
    }
}
package com.nasiat_muhib.classmate.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.core.Constants.CLASS_DETAILS_ALREADY_EXISTS
import com.nasiat_muhib.classmate.core.Constants.CLASS_DETAILS_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.CLASS_DETAILS_DOES_NOT_EXIST
import com.nasiat_muhib.classmate.core.Constants.COURSES_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.COURSE_ALREADY_EXISTS
import com.nasiat_muhib.classmate.core.Constants.COURSE_DOES_NOT_EXIST
import com.nasiat_muhib.classmate.core.Constants.COURSE_IS_ALREADY_CREATED_BY
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_CREATE_CLASS_DETAILS
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_DELETE_CLASS_DETAILS
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_GET_CLASS_DETAILS
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_GET_COURSE
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_UPDATE_CLASS_DETAILS
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions.getClassDetailsFromDocumentSnapshot
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions.getMapFromClassDetailsList
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.ClassDetailsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ClassDetailsRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore
) : ClassDetailsRepository {
    override fun createClassDetailsList(
        courseCode: String,
        classDetailsList: List<ClassDetails>
    ): Flow<ResponseState<List<ClassDetails>>> = callbackFlow {

        var errorMessage = validateCourse(courseCode)

        if(errorMessage == COURSE_ALREADY_EXISTS) {
            errorMessage = validateClassDetails(courseCode)

            if(errorMessage == CLASS_DETAILS_DOES_NOT_EXIST) {
                errorMessage = setClassDetailsListToFirebase(courseCode, classDetailsList)
            }
        }

        val response = if(errorMessage.isEmpty()) {
            ResponseState.Success(classDetailsList)
        } else {
            ResponseState.Failure(errorMessage)
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    override fun updateClassDetailsList(
        courseCode: String,
        classDetailsList: List<ClassDetails>
    ): Flow<ResponseState<List<ClassDetails>>> = callbackFlow {

        var errorMessage = validateCourse(courseCode)

        if(errorMessage == COURSE_ALREADY_EXISTS) {
            errorMessage = validateClassDetails(courseCode)
            if(errorMessage == CLASS_DETAILS_ALREADY_EXISTS) {
                errorMessage = updateClassDetailsListToFirebase(courseCode, classDetailsList)
            }
        }

        val response = if(errorMessage.isEmpty()) {
            ResponseState.Success(classDetailsList)
        } else {
            ResponseState.Failure(errorMessage)
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    override fun deleteClassDetailsList(courseCode: String): Flow<ResponseState<List<ClassDetails>>> = callbackFlow {

        var errorMessage = validateCourse(courseCode)

        if(errorMessage == COURSE_ALREADY_EXISTS) {
            errorMessage = validateClassDetails(courseCode)
            if(errorMessage == CLASS_DETAILS_ALREADY_EXISTS) {
                errorMessage = deleteClassDetailsListFromFirebase(courseCode)
            }
        }

        val response = if(errorMessage.isEmpty()) {
            ResponseState.Success(null)
        } else {
            ResponseState.Failure(errorMessage)
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    override fun getClassDetailsList(courseCode: String): Flow<ResponseState<List<ClassDetails>>> =
        callbackFlow {
            var response: ResponseState<List<ClassDetails>> = ResponseState.Loading

            val snapshotListener =
                firestoreRef.collection(CLASS_DETAILS_COLLECTION).document(courseCode)
                    .addSnapshotListener { classDetailsDocument, error ->
                        response = if (classDetailsDocument != null) {
                            val classDetailsList =
                                getClassDetailsFromDocumentSnapshot(classDetailsDocument)
                            ResponseState.Success(classDetailsList)
                        } else {
                            ResponseState.Failure("$UNABLE_TO_GET_CLASS_DETAILS :${error?.localizedMessage}")
                        }
                    }

            trySend(response).isSuccess

            awaitClose {
                snapshotListener.remove()
            }
        }


    // Utility Functions
    private fun setClassDetailsListToFirebase(
        courseCode: String,
        classDetailsList: List<ClassDetails>
    ): String {
        var returnValue = ""

        val map = getMapFromClassDetailsList(classDetailsList)

        firestoreRef.collection(COURSES_COLLECTION).document(courseCode).set(map)
            .addOnFailureListener {
                returnValue = UNABLE_TO_CREATE_CLASS_DETAILS
            }

        return returnValue
    }

    private fun deleteClassDetailsListFromFirebase(courseCode: String): String {

        var returnValue = ""

        firestoreRef.collection(CLASS_DETAILS_COLLECTION).document(courseCode).delete()
            .addOnFailureListener {
            returnValue = UNABLE_TO_DELETE_CLASS_DETAILS
        }

        return returnValue
    }

    private fun updateClassDetailsListToFirebase(
        courseCode: String,
        classDetailsList: List<ClassDetails>
    ): String {

        var returnValue = ""

        val map = getMapFromClassDetailsList(classDetailsList)
        firestoreRef.collection(CLASS_DETAILS_COLLECTION).document(courseCode).update(map)
            .addOnFailureListener {
                returnValue = UNABLE_TO_UPDATE_CLASS_DETAILS
            }

        return returnValue
    }


    // Validation
    private fun validateCourse(courseCode: String): String {
        var returnValue = ""

        firestoreRef.collection(COURSES_COLLECTION).document(courseCode).get()
            .addOnSuccessListener {
                returnValue = if (it.exists()) {
                    COURSE_ALREADY_EXISTS
                } else {
                    COURSE_DOES_NOT_EXIST
                }
            }.addOnFailureListener {
                returnValue = UNABLE_TO_GET_COURSE
            }

        return returnValue
    }

    private fun validateCreator(course: Course): String {
        var returnValue = COURSE_ALREADY_EXISTS

        firestoreRef.collection(COURSES_COLLECTION).document(course.code).get()
            .addOnSuccessListener {
                if (it[Constants.CREATOR] != course.creator) {
                    returnValue = COURSE_IS_ALREADY_CREATED_BY + ":" + it[Constants.CREATOR]
                }
            }.addOnFailureListener {
                returnValue = UNABLE_TO_GET_COURSE
            }

        return returnValue
    }

    private fun validateClassDetails(courseCode: String): String {
        var returnValue = ""

        firestoreRef.collection(COURSES_COLLECTION).document(courseCode).get()
            .addOnSuccessListener {
                returnValue = if(it.exists()) {
                    CLASS_DETAILS_ALREADY_EXISTS
                } else {
                    CLASS_DETAILS_DOES_NOT_EXIST
                }
            }.addOnFailureListener {
                returnValue = UNABLE_TO_GET_CLASS_DETAILS
            }

        return returnValue
    }


}
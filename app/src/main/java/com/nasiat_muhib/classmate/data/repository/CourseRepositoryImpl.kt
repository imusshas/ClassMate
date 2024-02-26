package com.nasiat_muhib.classmate.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.nasiat_muhib.classmate.core.Constants.CLASS_DETAILS_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.COURSES_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.COURSE_ALREADY_EXISTS
import com.nasiat_muhib.classmate.core.Constants.COURSE_DOES_NOT_EXIST
import com.nasiat_muhib.classmate.core.Constants.COURSE_IS_ALREADY_CREATED_BY
import com.nasiat_muhib.classmate.core.Constants.CREATED_COURSE
import com.nasiat_muhib.classmate.core.Constants.CREATOR
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_CREATE_COURSE
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_GET_CLASS_DETAILS
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_GET_COURSE
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_UPDATE_COURSE
import com.nasiat_muhib.classmate.core.Constants.USERS_COLLECTION
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions.getClassDetailsFromDocumentSnapshot
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions.getCourseFromDocumentSnapshot
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore,
    private val auth: FirebaseAuth
) : CourseRepository {

    private val coursesCollection = firestoreRef.collection(COURSES_COLLECTION)
    override fun createCourse(course: Course): Flow<ResponseState<Course>> = callbackFlow {
        var response: ResponseState<Course> = ResponseState.Loading

        try {
            coursesCollection.document(course.code).get()
                .addOnSuccessListener { courseSnapshot ->
                    val creator: String = courseSnapshot[CREATOR] as String
                    if (courseSnapshot.exists() && course.creator == creator) {
                        response = ResponseState.Failure(COURSE_ALREADY_EXISTS)
                    } else if (course.creator != creator) {
                        response = ResponseState.Failure("$COURSE_IS_ALREADY_CREATED_BY: $creator")
                    } else {
                        coursesCollection.document(course.code).set(course.toMap())
                            .addOnSuccessListener {
                                val userDocument = firestoreRef.collection(USERS_COLLECTION)
                                    .document(auth.currentUser?.email!!)
                                val createdCourse = mutableListOf<List<String>>()
                                userDocument.get().addOnSuccessListener {
                                    val createdCourseList: List<String> =
                                        it[CREATED_COURSE] as List<String>
                                    createdCourse.add(createdCourseList)
                                    createdCourse.add(listOf(course.code))
                                    userDocument.update(mapOf(CREATED_COURSE to createdCourse))
                                        .addOnSuccessListener {
                                            response = ResponseState.Success(course)
                                        }
                                }
                            }.addOnFailureListener {
                                response =
                                    ResponseState.Failure("$UNABLE_TO_CREATE_COURSE: ${it.localizedMessage}")
                            }
                    }
                }.addOnFailureListener { e ->
                    response =
                        ResponseState.Failure("$UNABLE_TO_CREATE_COURSE: ${e.localizedMessage}")
                }
        } catch (e: Exception) {
            response = ResponseState.Failure(e.localizedMessage!!)
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    override fun updateCourse(course: Course): Flow<ResponseState<Course>> = callbackFlow {
        var response: ResponseState<Course> = ResponseState.Loading

        val documentId = course.code

        coursesCollection.document(documentId).get()
            .addOnSuccessListener { courseSnapshot ->
                val courseDocument = coursesCollection.document(documentId)

                if (courseSnapshot.exists()) {
                    val courseCreator: String = courseSnapshot[CREATOR].toString()

                    if (course.creator == courseCreator) {
                        courseDocument.update(course.toMap())
                            .addOnSuccessListener {
                                response = ResponseState.Success(course)
                            }.addOnFailureListener {
                                response =
                                    ResponseState.Failure("$UNABLE_TO_UPDATE_COURSE: ${it.localizedMessage}")
                            }
                    } else {
                        response =
                            ResponseState.Failure("$COURSE_IS_ALREADY_CREATED_BY: $courseCreator")
                    }
                } else {
                    response = ResponseState.Failure(COURSE_DOES_NOT_EXIST)
                }
            }.addOnFailureListener {
                response = ResponseState.Failure("$UNABLE_TO_GET_COURSE: ${it.localizedMessage}")
            }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    /* TODO: Delete the course from enrolled students list & assigned teachers list */
    override fun deleteCourse(course: Course): Flow<ResponseState<Course>> = callbackFlow {
        var response: ResponseState<Course> = ResponseState.Loading

        val documentId = course.code

        coursesCollection.document(documentId).get().addOnSuccessListener { snapshot ->
            val courseDocument = coursesCollection.document(documentId)

            if (snapshot.exists()) {
                val courseCreator: String = snapshot[CREATOR].toString()
                if (course.creator == courseCreator) {
                    courseDocument.delete().addOnSuccessListener {
                        response = ResponseState.Success(course)
                    }.addOnFailureListener {
                        response = ResponseState.Failure(it.message.toString())
                    }
                } else {
                    response =
                        ResponseState.Failure("$COURSE_IS_ALREADY_CREATED_BY: $courseCreator")
                }
            } else {
                response = ResponseState.Failure(COURSE_DOES_NOT_EXIST)
            }
        }.addOnFailureListener {
            response = ResponseState.Failure(it.message.toString())
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    override fun getCourse(courseCode: String): Flow<ResponseState<Course>> = callbackFlow {

        var response: ResponseState<Course> = ResponseState.Loading

        val courseSnapshotListener = coursesCollection.document(courseCode)
            .addSnapshotListener { courseDocument, courseError ->
                response = if (courseDocument != null) {
                    val course = getCourseFromDocumentSnapshot(courseDocument)
                    ResponseState.Success(course)
                } else {
                    ResponseState.Failure("$UNABLE_TO_GET_COURSE: ${courseError?.localizedMessage}")
                }
            }

        trySend(response).isSuccess

        awaitClose {
            courseSnapshotListener.remove()
        }
    }

    override fun getCreatedCourseList(email: String): Flow<ResponseState<List<Course>>> =
        callbackFlow {

            var response: ResponseState<List<Course>> = ResponseState.Loading
            var snapshotListener = ListenerRegistration { }

            firestoreRef.collection(USERS_COLLECTION).document(email).get()
                .addOnSuccessListener { userSnapshot ->
                    if (userSnapshot.exists()) {
                        val courseCodes: List<String> = userSnapshot[CREATED_COURSE] as List<String>

                        val courseList = mutableListOf<Course>()
                        courseCodes.forEach {
                            snapshotListener =
                                firestoreRef.collection(COURSES_COLLECTION).document(it)
                                    .addSnapshotListener { value, error ->
                                        response = if (value != null) {
                                            courseList.add(getCourseFromDocumentSnapshot(value))
                                            ResponseState.Success(courseList)
                                        } else {
                                            ResponseState.Failure("$UNABLE_TO_GET_COURSE: $it: ${error?.localizedMessage}")
                                        }
                                    }
                        }
                    }
                }


            trySend(response).isSuccess

            awaitClose {
                snapshotListener.remove()
            }
        }


}
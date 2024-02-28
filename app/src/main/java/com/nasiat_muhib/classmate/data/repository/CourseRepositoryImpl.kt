package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.Constants.COURSES_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.COURSE_ALREADY_EXISTS
import com.nasiat_muhib.classmate.core.Constants.COURSE_DOES_NOT_EXIST
import com.nasiat_muhib.classmate.core.Constants.COURSE_IS_ALREADY_CREATED_BY
import com.nasiat_muhib.classmate.core.Constants.CREATED_COURSE
import com.nasiat_muhib.classmate.core.Constants.CREATOR
import com.nasiat_muhib.classmate.core.Constants.ENROLLED_COURSE
import com.nasiat_muhib.classmate.core.Constants.ENROLLED_STUDENTS
import com.nasiat_muhib.classmate.core.Constants.REQUESTED_COURSE
import com.nasiat_muhib.classmate.core.Constants.TAG
import com.nasiat_muhib.classmate.core.Constants.TEACHER
import com.nasiat_muhib.classmate.core.Constants.TEACHER_DOES_NOT_EXIST
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_CREATE_COURSE
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_DELETE_COURSE
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_DELETE_COURSE_FROM_USERS
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_GET_COURSE
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_GET_TEACHER
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_SEND_COURSE_REQUEST
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_UPDATE_COURSE_TEACHER
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_UPDATE_CREATED_COURSE
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_UPDATE_ENROLLED_STUDENTS
import com.nasiat_muhib.classmate.core.Constants.UNABLE_TO_UPDATE_USER_COURSE_LIST
import com.nasiat_muhib.classmate.core.Constants.USERS_COLLECTION
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions.getCourseFromDocumentSnapshot
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions.getUserFromDocumentSnapshot
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore,
) : CourseRepository {

    private val coursesCollection = firestoreRef.collection(COURSES_COLLECTION)
    override fun createCourse(course: Course): Flow<ResponseState<Course>> = callbackFlow {

        var errorMessage = validateCourse(course.code)

        if (errorMessage == COURSE_ALREADY_EXISTS) {
            errorMessage = validateCreator(course)
        } else if (errorMessage == COURSE_DOES_NOT_EXIST) {
            errorMessage = validateTeacher(course.teacher)

            errorMessage.ifEmpty {
                errorMessage = setCourseToFirebase(course)
            }

            errorMessage.ifEmpty {
                errorMessage = sendCourseRequest(course.teacher, course.code)
            }

            errorMessage.ifEmpty {
                errorMessage = setCreatedCourse(course.creator, course.code)
            }
        }


        val response = if (errorMessage.isEmpty()) {
            ResponseState.Success(course)
        } else {
            ResponseState.Failure(errorMessage)
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    override fun updateCourse(course: Course): Flow<ResponseState<Course>> = callbackFlow {

        var errorMessage = validateCourse(course.code)

        if (errorMessage == COURSE_ALREADY_EXISTS) {
            errorMessage = validateCreator(course)

            if (errorMessage == COURSE_ALREADY_EXISTS) {
                errorMessage = updateCourseTeacher(course)
            } else if (errorMessage != UNABLE_TO_GET_COURSE) {
                errorMessage = updateEnrolledStudents(course)
            }
        }

        val response = if (errorMessage.isEmpty()) {
            ResponseState.Success(course)
        } else {
            ResponseState.Failure(errorMessage)
        }

        trySend(response).isSuccess

        awaitClose {

        }
    }

    override fun deleteCourse(course: Course): Flow<ResponseState<Course>> = callbackFlow {
        var errorMessage = validateCourse(course.code)

        if (errorMessage == COURSE_ALREADY_EXISTS) {
            errorMessage = validateCreator(course)
            if (errorMessage == COURSE_ALREADY_EXISTS) {
                errorMessage = when (getAllUsers(course.code)) {
                    "" -> UNABLE_TO_DELETE_COURSE_FROM_USERS
                    UNABLE_TO_GET_COURSE -> UNABLE_TO_GET_COURSE
                    else -> {
                        val courseUsers: List<String> = if (getAllUsers(course.code) is List<*>)
                            getAllUsers(course.code) as List<String> else emptyList()
                        deleteCourseFromUsers(courseUsers, course.code)
                    }
                }

                if(errorMessage.isEmpty()) {
                    errorMessage = deleteCourseFromFirebase(course.code)
                }
            }
        }

        val response = if(errorMessage.isEmpty()) {
            ResponseState.Success(course)
        } else {
            ResponseState.Failure(errorMessage)
        }

        trySend(response)

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
                    ResponseState.Failure(UNABLE_TO_GET_COURSE)
                }
            }

        trySend(response).isSuccess

        awaitClose {
            courseSnapshotListener.remove()
        }
    }


    // Utility functions
    private fun setCreatedCourse(email: String, courseCode: String): String {

        val userDocument = firestoreRef.collection(USERS_COLLECTION).document(email)

        var returnValue = ""

        userDocument.get().addOnSuccessListener { courseListSnapshot ->
            val courseList: List<String> =
                if (courseListSnapshot[CREATED_COURSE] is List<*> && courseListSnapshot[CREATED_COURSE] != null) courseListSnapshot[CREATED_COURSE] as List<String> else emptyList()
            val createdCourse = mutableListOf<String>()
            courseList.forEach {
                createdCourse.add(it)
            }
            createdCourse.add(courseCode)
            userDocument.update(mapOf(CREATED_COURSE to createdCourse)).addOnFailureListener {
                returnValue = UNABLE_TO_UPDATE_CREATED_COURSE
            }
        }

        return returnValue
    }

    private fun sendCourseRequest(email: String, courseCode: String): String {

        val userDocument = firestoreRef.collection(USERS_COLLECTION).document(email)

        var returnValue = ""

        userDocument.get().addOnSuccessListener { userSnapshot ->
            val courseList: List<String> =
                if (userSnapshot[REQUESTED_COURSE] is List<*> && userSnapshot[REQUESTED_COURSE] != null) userSnapshot[REQUESTED_COURSE] as List<String> else emptyList()
            val requestedCourse = mutableListOf<String>()
            courseList.forEach {
                requestedCourse.add(it)
            }
            requestedCourse.add(courseCode)
            userDocument.update(mapOf(REQUESTED_COURSE to requestedCourse))
                .addOnFailureListener {
                    returnValue = UNABLE_TO_SEND_COURSE_REQUEST
                }
        }.addOnFailureListener {
            returnValue = UNABLE_TO_GET_TEACHER
        }

        return returnValue
    }

    private fun setCourseToFirebase(course: Course): String {
        var returnValue = ""

        firestoreRef.collection(COURSES_COLLECTION).document(course.code).set(course.toMap())
            .addOnFailureListener {
                returnValue = UNABLE_TO_CREATE_COURSE
            }

        return returnValue
    }

    private fun updateEnrolledStudents(course: Course): String {

        var returnValue = ""


        if (course.enrolledStudents.isNotEmpty()) {
            coursesCollection.document(course.code).get().addOnSuccessListener { courseDocument ->
                val enrolledList: List<String> =
                    if (courseDocument[ENROLLED_STUDENTS] is List<*> && courseDocument[ENROLLED_STUDENTS] != null) courseDocument[ENROLLED_STUDENTS] as List<String> else emptyList()

                val mutableList: MutableList<String> = mutableListOf()
                enrolledList.forEach {
                    mutableList.add(it)
                }

                course.enrolledStudents.forEach {
                    mutableList.add(it)
                }

                coursesCollection.document(course.code)
                    .update(mapOf(ENROLLED_STUDENTS to mutableList)).addOnFailureListener {
                        returnValue = UNABLE_TO_UPDATE_ENROLLED_STUDENTS
                    }
            }
        }

        return returnValue
    }

    private fun updateCourseTeacher(course: Course): String {

        var returnValue = ""

        if (course.teacher.isNotEmpty()) {
            coursesCollection.document(course.code).update(mapOf(TEACHER to course.teacher))
                .addOnFailureListener {
                    returnValue = UNABLE_TO_UPDATE_COURSE_TEACHER
                }
        }

        return returnValue
    }

    private fun getAllUsers(courseCode: String): Any {
        var returnValue: Any = ""

        firestoreRef.collection(COURSES_COLLECTION).document(courseCode).get()
            .addOnSuccessListener { courseSnapshot ->
                val course = getCourseFromDocumentSnapshot(courseSnapshot)
                val courseUsersList = mutableListOf<String>()

                courseUsersList.add(course.creator)
                courseUsersList.add(course.teacher)
                course.enrolledStudents.forEach {
                    courseUsersList.add(it)
                }

                returnValue = courseUsersList
            }
            .addOnFailureListener {
                returnValue = UNABLE_TO_GET_COURSE
            }

        return returnValue
    }

    private fun deleteCourseFromUsers(courseUsers: List<String>, courseCode: String): String {

        var returnValue = ""

        val usersCollection = firestoreRef.collection(USERS_COLLECTION)
        courseUsers.forEach { courseUser ->
            usersCollection.document(courseUser)
                .get()
                .addOnSuccessListener { userSnapshot ->
                    val user = getUserFromDocumentSnapshot(userSnapshot)
                    val usersCourses = mutableListOf<String>()

                    // For created courses
                    user.createdCourse.forEach {
                        if (it != courseCode) {
                            usersCourses.add(it)
                        }
                    }
                    returnValue = updateCourseOfSingleUser(
                        usersCollection.document(courseCode),
                        usersCourses,
                        CREATED_COURSE
                    )
                    usersCourses.removeAll(usersCourses)

                    // For enrolled courses
                    user.enrolledCourse.forEach {
                        if (it != courseCode) {
                            usersCourses.add(it)
                        }
                    }
                    returnValue = updateCourseOfSingleUser(
                        usersCollection.document(courseCode),
                        usersCourses,
                        ENROLLED_COURSE
                    )
                    usersCourses.removeAll(usersCourses)

                    // For requested courses
                    user.requestedCourse.forEach {
                        if (it != courseCode) {
                            usersCourses.add(it)
                        }
                    }
                    returnValue = updateCourseOfSingleUser(
                        usersCollection.document(courseCode),
                        usersCourses,
                        REQUESTED_COURSE
                    )
                    usersCourses.removeAll(usersCourses)

                }.addOnFailureListener {
                    returnValue = UNABLE_TO_DELETE_COURSE_FROM_USERS
                }
        }

        return returnValue
    }


    private fun updateCourseOfSingleUser(
        userDocument: DocumentReference,
        courseCodes: List<String>,
        field: String
    ): String {
        var returnValue = ""

        userDocument.update(mapOf(field to courseCodes))
            .addOnFailureListener {
                /*TODO: Check return value*/
                returnValue = UNABLE_TO_UPDATE_USER_COURSE_LIST
            }

        return returnValue
    }

    private fun deleteCourseFromFirebase(courseCode: String): String {

        var returnValue = ""

        coursesCollection.document(courseCode).delete().addOnFailureListener {
            returnValue = UNABLE_TO_DELETE_COURSE
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
                if (it[CREATOR] != course.creator) {
                    returnValue = COURSE_IS_ALREADY_CREATED_BY + ":" + it[CREATOR]
                }
            }.addOnFailureListener {
                returnValue = UNABLE_TO_GET_COURSE
            }

        return returnValue
    }

    private fun validateTeacher(email: String): String {
        var returnValue = ""

        firestoreRef.collection(USERS_COLLECTION).document(email).get()
            .addOnSuccessListener {
                if (!it.exists()) {
                    returnValue = TEACHER_DOES_NOT_EXIST
                }
            }.addOnFailureListener {
                returnValue = UNABLE_TO_GET_TEACHER
            }

        return returnValue
    }

}
package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getCourseFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.CLASSES_COLLECTION
import com.nasiat_muhib.classmate.strings.COURSES
import com.nasiat_muhib.classmate.strings.COURSES_COLLECTION
import com.nasiat_muhib.classmate.strings.COURSE_CLASSES
import com.nasiat_muhib.classmate.strings.REQUESTED_COURSES
import com.nasiat_muhib.classmate.strings.USERS_COLLECTION
import com.nasiat_muhib.classmate.strings.WEEKDAY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore,
) : CourseRepository {

    private val usersCollection = firestoreRef.collection(USERS_COLLECTION)
    private val coursesCollection = firestoreRef.collection(COURSES_COLLECTION)
    private val classesCollection = firestoreRef.collection(CLASSES_COLLECTION)
    override fun createCourse(
        course: Course,
        classDetailsSet: Set<ClassDetails>,
    ): Flow<Pair<DataState<Course>, DataState<Set<ClassDetails>>>> = flow {
        emit(Pair(DataState.Loading, DataState.Loading))

        val requestList = getTeacherCourseRequestSet(teacher = course.courseTeacher)
        val courseList = getTeachersCourse(teacher = course.courseTeacher)

        val allCourses = requestList + courseList

        var isSuccessful: Boolean =
            !allCourses.contains("${course.courseDepartment}:${course.courseCode}")

//        Log.d(TAG, "createCourse: result on matching teachers courses: $isSuccessful")

        if (isSuccessful) {

            val courseId = "${course.courseDepartment}:${course.courseCode}"

            // Search weather the course exists or not
            isSuccessful = searchForExistence(coursesCollection.document(courseId))
//            Log.d(TAG, "createCourse: result on course existence: $isSuccessful")
            if (isSuccessful) {

                // If the course exists send delete request
                isSuccessful = sendRequest(course.courseCreator, courseId)
            } else {

                // If the course doesn't exist send request to teacher
                isSuccessful = sendRequest(course.courseTeacher, courseId)
//                Log.d(TAG, "createCourse: send teacher request: $isSuccessful")

                if (isSuccessful) {

                    // Course Document
                    val docRef = coursesCollection.document(courseId)
                    // Create Course to firestore
                    isSuccessful = createCourseToFirebase(docRef, course)
//                    Log.d(TAG, "createCourse: result on create course to firebase: $isSuccessful")

                    if (isSuccessful) {
                        for (classes in classDetailsSet) {
                            val index = classDetailsSet.indexOf(classes)
                            val classId = "class${index + 1}"
                            val classDocRef = classesCollection
                                .document(classes.weekDay)
                                .collection(courseId)
                                .document(classId)
                            isSuccessful = createClassesToFirebase(classDocRef, classes)

//                                Log.d(TAG, "createCourse: result on creating class details list: $isSuccessful")

                            if (!isSuccessful) {
                                break
                            } else {
                                getAndUpdateArrayToFirebase(docRef, COURSE_CLASSES, classId)
                            }
                        }
                    }
                }
            }
        }


        if (isSuccessful) {
            emit(Pair(DataState.Success(course), DataState.Success(classDetailsSet)))
        } else {
            emit(Pair(DataState.Error(""), DataState.Error("")))
        }

    }

    override fun getCourse(email: String, courseId: String): Flow<DataState<Course>> =
        callbackFlow {

            var data: DataState<Course> = DataState.Loading

            coursesCollection.document(courseId)
                .addSnapshotListener { value, error ->
                    if (value != null) {
                        val course = getCourseFromFirestoreDocument(value)

                    }
                }

        }

    override fun updateCourse(): Flow<DataState<Course>> {
        TODO("Not yet implemented")
    }

    override fun deleteCourse(): Flow<DataState<Course>> {
        TODO("Not yet implemented")
    }


    private suspend fun searchForExistence(
        docRef: DocumentReference,
    ): Boolean {
        var isSuccessful = false
        docRef.get()
            .addOnSuccessListener {
                if (it.exists() && it != null) {
                    isSuccessful = true
                }
            }.addOnFailureListener {
                Log.d(TAG, "getUserFromFirebase: ${it.localizedMessage}")
            }.await()

        return isSuccessful
    }

    private suspend fun sendRequest(
        user: String,
        courseId: String,
    ): Boolean {

        val docRef = usersCollection.document(user)
        return getAndUpdateArrayToFirebase(docRef, REQUESTED_COURSES, courseId)
    }


    private suspend fun getTeacherCourseRequestSet(teacher: String): Set<String> {

        val requestSet = mutableSetOf<String>()

        usersCollection.document(teacher).get()
            .addOnCompleteListener { courses ->
                if (courses.isSuccessful) {
                    val requests = courses.result[REQUESTED_COURSES]
                    val requestedCourses: Set<String> =
                        if (requests is Set<*>) requestSet else emptySet()
                    requestedCourses.forEach {
                        requestSet.add(it)
                    }
                }
            }.addOnFailureListener {
                Log.d(TAG, "getTeacherCourseRequestSet: ${it.localizedMessage}")
            }.await()

        return requestSet
    }

    private suspend fun getTeachersCourse(teacher: String): Set<String> {

        val coursesSet = mutableSetOf<String>()

        usersCollection.document(teacher).get()
            .addOnCompleteListener { courses ->
                if (courses.isSuccessful) {
                    val requests = courses.result[COURSES]
                    val requestedCourses: Set<String> =
                        if (requests is Set<*>) coursesSet else emptySet()
                    requestedCourses.forEach {
                        coursesSet.add(it)
                    }
                }
            }.addOnFailureListener {
                Log.d(TAG, "getTeacherCourseRequestSet: ${it.localizedMessage}")
            }.await()

        return coursesSet
    }

    private suspend fun createCourseToFirebase(
        courseDocument: DocumentReference,
        course: Course,
    ): Boolean {

        var isSuccessful = false

        courseDocument.set(course.toMap())
            .addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.addOnFailureListener {
                Log.d(TAG, "createCourseToFirebase: ${it.localizedMessage}")
            }.await()

        return isSuccessful
    }

    private suspend fun createClassesToFirebase(
        classesDocument: DocumentReference,
        classDetails: ClassDetails,
    ): Boolean {
        var isSuccessful = false

        classesDocument.set(classDetails.toMap())
            .addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.addOnFailureListener {
                Log.d(TAG, "createClassesToFirebase: ${it.localizedMessage}")
            }.await()

        return isSuccessful
    }

    private suspend fun getAndUpdateArrayToFirebase(
        docRef: DocumentReference,
        field: String,
        value: String,
    ): Boolean {

        var isSuccessful = false

        val arrayList = mutableSetOf<String>()

        docRef.get()
            .addOnCompleteListener { classSnapshot ->
                val prevList = if (classSnapshot.result[field] != null) classSnapshot.result[field] as List<String> else emptyList()
                prevList.forEach {
                    arrayList.add(it)
                }
                isSuccessful = classSnapshot.isSuccessful
            }.await()

        Log.d(TAG, "getAndUpdateArrayToFirebase: result for getting array: $arrayList")

        if (isSuccessful) {
            arrayList.add(value)
            docRef.update(mapOf(field to arrayList.toList()))
                .addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.await()

//            Log.d(TAG, "getAndUpdateArrayToFirebase: result for updating array: $isSuccessful")
        }

        return isSuccessful
    }


    companion object {
        const val TAG = "CourseRepositoryImpl"
    }
}
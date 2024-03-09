package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getClassDetailsFromFirestoreDocument
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getCourseFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.CLASSES_COLLECTION
import com.nasiat_muhib.classmate.strings.COURSES
import com.nasiat_muhib.classmate.strings.COURSES_COLLECTION
import com.nasiat_muhib.classmate.strings.COURSE_CREATOR
import com.nasiat_muhib.classmate.strings.REQUESTED_COURSES
import com.nasiat_muhib.classmate.strings.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
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
    ): Flow<Pair<DataState<Course>, DataState<List<ClassDetails>>>> =
        flow {


            emit(Pair(DataState.Loading, DataState.Loading))


            val courseId = "${course.courseDepartment}:${course.courseCode}"
            val courseDoc = coursesCollection.document(courseId)

            val response = searchForExistence(courseDoc)

            if (response != null) {
                sendRequest(response, courseId)
                emit(
                    Pair(
                        DataState.Error("Course is already created by $response. A course delete request has been sent."),
                        DataState.Error("Course Already exists.")
                    )
                )
                return@flow
            }

            var isSuccessful = sendRequest(course.courseTeacher, courseId)

            if (!isSuccessful) {
                Log.d(TAG, "createCourse: Unable to send course request to the teacher")
                emit(
                    Pair(
                        DataState.Error("Unable to send course request to the teacher"),
                        DataState.Error("Unable to send course request to the teacher")
                    )
                )
                return@flow
            }

            val creatorDoc = usersCollection.document(course.courseCreator)
            Log.d(TAG, "createCourse: creator: ${course.courseCreator}")

            isSuccessful = getAndUpdateArrayToFirebase(creatorDoc, COURSES, courseId)
            if (!isSuccessful) {
                Log.d(TAG, "createCourse: Unable to update creator's course list")
                emit(
                    Pair(
                        DataState.Error("Unable to update creator's course list"),
                        DataState.Error("Unable to update creator's course list")
                    )
                )
                return@flow
            }

            for (classes in classDetailsSet) {
                val classDetails = classes.copy(
                    classDepartment = course.courseDepartment,
                    classCourseCode = course.courseCode,
                    classNo = classDetailsSet.indexOf(classes)
                )
                val classId = "${classDetails.classDepartment}:${classDetails.classCourseCode}:${classDetails.classNo}"
                val classDoc = classesCollection.document(classId)
                isSuccessful = createClassesToFirebase(classDoc, classDetails)

                if (!isSuccessful) {
                    emit(
                        Pair(
                            DataState.Error("Failed to create course due to failure in creation of classes"),
                            DataState.Error("Failed fo create class: $classId")
                        )
                    )
                    Log.d(TAG, "createCourse: Failed to create class: $classId")
                    return@flow
                }
            }


            isSuccessful = createCourseToFirebase(courseDoc, course)

            if (!isSuccessful) {
                emit(
                    Pair(
                        DataState.Error("Failed to create course"),
                        DataState.Error("Classes were created successfully but failed to create course")
                    )
                )
                Log.d(TAG, "createCourse: Failed to create course")
                return@flow
            }

            emit(Pair(DataState.Success(course), DataState.Success(classDetailsSet.toList())))


        }.catch {
            Log.d(TAG, "createCourse: $it")
        }


    override fun getCourseList(courseIds: List<String>): Flow<List<Course>> =
        callbackFlow {

            if (courseIds.isEmpty()) {
                trySend(emptyList())
                Log.d(TAG, "getCourseList: User have no courses")
                return@callbackFlow
            }


            val snapshotListener = coursesCollection
                .addSnapshotListener { value, error ->

                    val courseList = mutableListOf<Course>()
                    value?.documents?.forEach { documentSnapshot ->
                        if (courseIds.contains(documentSnapshot.id)) {
                            val course = getCourseFromFirestoreDocument(documentSnapshot)
//                            if (!course.pendingStatus) {
                                courseList.add(course)
//                            }
                        }

                        trySend(courseList).isSuccess
                    }

                    error?.let {
                        Log.d(TAG, "getCourseList: ${it.localizedMessage}")
                        return@addSnapshotListener
                    }
                }

            awaitClose {
                snapshotListener.remove()
            }
        }.catch {
            Log.d(TAG, "getCourseList: $it")
        }

    override fun getClassDetailsList(courseId: String): Flow<List<ClassDetails>> =
        callbackFlow {

            val classDetailsList = mutableListOf<ClassDetails>()
            val snapshotListener = classesCollection.document(courseId)
                .addSnapshotListener { value, error ->
                    if (value != null) {
                        val classDetails = getClassDetailsFromFirestoreDocument(value)
                        classDetailsList.add(classDetails)
                    } else if (error != null) {
                        Log.d(TAG, "getClassDetailsList: ${error.localizedMessage}")
                        return@addSnapshotListener
                    }

                    trySend(classDetailsList).isSuccess
                }


            awaitClose {
                snapshotListener.remove()
            }

        }


    override fun updateCourse(): Flow<DataState<Course>> {
        TODO("Not yet implemented")
    }

    override fun deleteCourse(): Flow<DataState<Course>> {
        TODO("Not yet implemented")
    }

    override fun updateClass(details: ClassDetails): Flow<DataState<ClassDetails>> = flow {
        emit(DataState.Loading)

        val classId = "${details.classDepartment}:${details.classCourseCode}:${details.classNo}"
        var isSuccessful = false

        classesCollection.document(classId).update(details.toMap())
            .addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.addOnFailureListener {
                Log.d(TAG, "updateClass: ${it.localizedMessage}")
            }.await()

        if (isSuccessful) {
            emit(DataState.Success(details))
        } else {
            emit(DataState.Error("Unable to update details: $classId"))
        }

    }.catch {
        Log.d(TAG, "updateClass: $it")
    }


    private suspend fun searchForExistence(
        courseRef: DocumentReference,
    ): String? {
        var respose: String? = null
        courseRef.get()
            .addOnSuccessListener {
                if (it.exists() && it != null) {
                    respose = it[COURSE_CREATOR].toString()
                }
            }.addOnFailureListener {
                Log.d(TAG, "searchForExistence: ${it.localizedMessage}")
            }.await()

        return respose
    }

    private suspend fun sendRequest(
        user: String,
        courseId: String,
    ): Boolean {

        val docRef = usersCollection.document(user)
        return getAndUpdateArrayToFirebase(docRef, REQUESTED_COURSES, courseId)
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

        val arraySet = mutableSetOf<String>()

        docRef.get()
            .addOnCompleteListener { classSnapshot ->
                val prevList =
                    if (classSnapshot.result[field] != null) classSnapshot.result[field] as List<String> else emptyList()
                prevList.forEach {
                    arraySet.add(it)
                }
                isSuccessful = classSnapshot.isSuccessful
            }.addOnFailureListener {
                Log.d(TAG, "getAndUpdateArrayToFirebase: getting: ${it.localizedMessage}")
            }
            .await()

        Log.d(TAG, "getAndUpdateArrayToFirebase: result for getting array: $arraySet")

        if (isSuccessful) {
            arraySet.add(value)
            docRef.update(mapOf(field to arraySet.toList()))
                .addOnCompleteListener {
                    isSuccessful = it.isSuccessful
                }.addOnFailureListener {
                    Log.d(TAG, "getAndUpdateArrayToFirebase: ${it.localizedMessage}")
                }.await()

            Log.d(TAG, "getAndUpdateArrayToFirebase: result for updating array: $isSuccessful")
        }

        return isSuccessful
    }


    companion object {
        const val TAG = "CourseRepositoryImpl"
    }
}
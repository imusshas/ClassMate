package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getCourseFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.ASSIGNMENTS_COLLECTION
import com.nasiat_muhib.classmate.strings.CLASSES_COLLECTION
import com.nasiat_muhib.classmate.strings.COURSES
import com.nasiat_muhib.classmate.strings.COURSES_COLLECTION
import com.nasiat_muhib.classmate.strings.COURSE_CREATOR
import com.nasiat_muhib.classmate.strings.EMAIL
import com.nasiat_muhib.classmate.strings.PENDING_STATUS
import com.nasiat_muhib.classmate.strings.REQUESTED_COURSES
import com.nasiat_muhib.classmate.strings.TERM_TESTS_COLLECTION
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
    private val termTestsCollection = firestoreRef.collection(TERM_TESTS_COLLECTION)
    private val assignmentsCollection = firestoreRef.collection(ASSIGNMENTS_COLLECTION)





    companion object {
        const val TAG = "CourseRepositoryImpl"
    }

    override fun createCourse(
        course: Course,
        classDetailsSet: Set<ClassDetails>,
    ): Flow<Pair<DataState<Course>, DataState<List<ClassDetails>>>> = flow {
        emit(Pair(DataState.Loading, DataState.Loading))
        val courseId = "${course.courseDepartment}:${course.courseCode}"

        // Send course Delete request
        coursesCollection.document(courseId).get().addOnSuccessListener { courseSnapshot ->
            if (courseSnapshot.exists() && courseSnapshot != null) {
                val creatorEmail = courseSnapshot[COURSE_CREATOR].toString()
                usersCollection.document(creatorEmail).get().addOnSuccessListener { creator ->
                    if (creator.exists() && creator != null) {
                        val requestedCourses = if (creator[REQUESTED_COURSES] == null) mutableListOf() else creator[REQUESTED_COURSES] as MutableList<String>
                        requestedCourses.add(courseId)
                        usersCollection.document(creatorEmail).update(REQUESTED_COURSES,requestedCourses).addOnFailureListener {
                            Log.d(TAG, "searchForExistenceAndSendRequest: request update: ${it.localizedMessage}")
                        }.addOnFailureListener {
//                            Log.d(TAG, "createCourse: ${it.localizedMessage}")
                            Log.d(TAG, "searchForExistenceAndSendRequest: ${it.localizedMessage}")
                        }
                    }
                }.addOnFailureListener {
                    Log.d(TAG, "createCourse: ${it.localizedMessage}")
                    return@addOnFailureListener
                }
            }
        }.addOnFailureListener {
//            Log.d(TAG, "createCourse: ${it.localizedMessage}")
            return@addOnFailureListener
        }


        // Send Teacher request
        usersCollection.document(course.courseTeacher).get().addOnSuccessListener { creator ->
            if (creator.exists() && creator != null) {
                val requestedCourses = if (creator[REQUESTED_COURSES] == null) mutableListOf() else creator[REQUESTED_COURSES] as MutableList<String>
                requestedCourses.add(courseId)
                usersCollection.document(course.courseTeacher).update(REQUESTED_COURSES,requestedCourses).addOnFailureListener {
//                    Log.d(TAG, "createCourse: ${it.localizedMessage}")
                    return@addOnFailureListener
                }
            }
        }.addOnFailureListener {
//            Log.d(TAG, "createCourse: ${it.localizedMessage}")
            return@addOnFailureListener
        }.await()

        // Update Creators Course List
        usersCollection.document(course.courseCreator).get().addOnSuccessListener { creator ->
            if (creator.exists() && creator != null) {
                val creatorCourses = if (creator[COURSES] == null) mutableListOf() else creator[COURSES] as MutableList<String>
                creatorCourses.add(courseId)
                usersCollection.document(course.courseCreator).update(COURSES,creatorCourses).addOnFailureListener {
//                    Log.d(TAG, "createCourse: ${it.localizedMessage}")
                    return@addOnFailureListener
                }
            }
        }.addOnFailureListener {
//            Log.d(TAG, "createCourse: ${it.localizedMessage}")
            return@addOnFailureListener
        }.await()

        // Create Course Classes
        classDetailsSet.forEach { classDetails ->
            val classIndex = classDetailsSet.indexOf(classDetails)
            val classId = "$courseId:$classIndex"
            val details = classDetails.copy(classNo = classIndex, classCourseCode = course.courseCode, classDepartment = course.courseDepartment)
//            Log.d(TAG, "createCourse: $details")
            classesCollection.document(classId).set(details.toMap()).addOnFailureListener {
//                Log.d(TAG, "createCourse: ${it.localizedMessage}")
                return@addOnFailureListener
            }.await()
        }

        
        // Create Course
        coursesCollection.document(courseId).set(course.toMap()).addOnFailureListener {
//            Log.d(TAG, "createCourse: ${it.localizedMessage}")
            return@addOnFailureListener
        }.await()
        
        emit(Pair(DataState.Success(course), DataState.Success(classDetailsSet.toList())))

    }.catch {
        Log.d(TAG, "createCourse: ${it.localizedMessage}")
    }




    override fun getRequestedCourses(courseIds: List<String>): Flow<List<Course>> = callbackFlow {

        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val pendingCourses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
                courseIds.forEach {
                    val course = getCourseFromFirestoreDocument(document)
                    if (course.pendingStatus && document.id.contains(it)) {
                        pendingCourses.add(course)
                    }
                }
                trySend(pendingCourses).isSuccess
            }

            if (error != null) {
                Log.d(TAG, "getCourseList: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
//        Log.d(TAG, "getCourseList: ${it.localizedMessage}")
    }

    override fun getCreatedCourses(
        courseIds: List<String>,
        creatorEmail: String,
    ): Flow<List<Course>> = callbackFlow {

        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val pendingCourses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
                courseIds.forEach {
                    val course = getCourseFromFirestoreDocument(document)
                    if (!course.pendingStatus && document.id.contains(it) && document[COURSE_CREATOR] == creatorEmail) {
                        pendingCourses.add(course)
                    }
                }
                trySend(pendingCourses).isSuccess
            }

            if (error != null) {
                Log.d(TAG, "getCourseList: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
//        Log.d(TAG, "getCourseList: ${it.localizedMessage}")
    }

    override fun getPendingCourseList(courseIds: List<String>): Flow<List<Course>> = callbackFlow {
//        Log.d(TAG, "getPendingCourseList: $courseIds")
        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val pendingCourses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
//                Log.d(TAG, "getPendingCourseList: ${document.data}")
                courseIds.forEach {
//                    Log.d(TAG, "getPendingCourseList: $it, ${document.id}")
                    val course = getCourseFromFirestoreDocument(document)
                    if (course.pendingStatus && document.id.contains(it)) {
                        pendingCourses.add(course)
//                        Log.d(TAG, "getPendingCourseList: true")
                    }
                }
                trySend(pendingCourses).isSuccess
            }
            
            if (error != null) {
                Log.d(TAG, "getPendingCourseList: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
//        Log.d(TAG, "getPendingCourseList: $it")
    }

    override fun getCourses(courseIds: List<String>): Flow<List<Course>> = callbackFlow {
//        Log.d(TAG, "getPendingCourseList: $courseIds")
        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val courses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
//                Log.d(TAG, "getPendingCourseList: ${document.data}")
                courseIds.forEach {
//                    Log.d(TAG, "getPendingCourseList: $it, ${document.id}")
                    val course = getCourseFromFirestoreDocument(document)
                    if (!course.pendingStatus && document.id.contains(it)) {
                        courses.add(course)
//                        Log.d(TAG, "getPendingCourseList: true")
                    }
                }
                trySend(courses).isSuccess
            }

            if (error != null) {
                Log.d(TAG, "getPendingCourseList: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
//        Log.d(TAG, "getPendingCourseList: $it")
    }

    override fun getClassDetailsList(courseId: String): Flow<List<ClassDetails>> {
        TODO("Not yet implemented")
    }

    override fun updateCourse(): Flow<DataState<Course>> {
        TODO("Not yet implemented")
    }

    override fun acceptCourse(course: Course): Flow<DataState<Course>> = flow<DataState<Course>> {
        emit(DataState.Loading)

        val courseId = "${course.courseDepartment}:${course.courseCode}"

        // Change Teacher's request status
        usersCollection.document(course.courseTeacher).get().addOnSuccessListener {
            if (it.exists() && it != null) {
                val requestedCourses = if (it[REQUESTED_COURSES] != null) it[REQUESTED_COURSES] as MutableList<String> else mutableListOf()
                val courses = if (it[COURSES] != null) it[COURSES] as MutableList<String> else mutableListOf()

                requestedCourses.remove(courseId)
                courses.add(courseId)

                usersCollection.document(course.courseTeacher).update(COURSES, courses).addOnFailureListener {
                    return@addOnFailureListener
                }
                usersCollection.document(course.courseTeacher).update(REQUESTED_COURSES, requestedCourses).addOnFailureListener {
                    return@addOnFailureListener
                }
            }
        }

        // Change course pending status
        coursesCollection.document(courseId).update(PENDING_STATUS, false).addOnSuccessListener {
            return@addOnSuccessListener
        }

    }.catch {
        Log.d(TAG, "acceptCourse: ${it.localizedMessage}")
    }

    override fun deleteCourse(course: Course): Flow<DataState<Course>> = flow<DataState<Course>> {
        emit(DataState.Loading)

        val courseId = "${course.courseDepartment}:${course.courseCode}"

        // Delete the course from teacher
        usersCollection.document(course.courseTeacher).get().addOnSuccessListener { courseIds ->
            val courses = if (courseIds[COURSES]  != null) courseIds[COURSES] as MutableList<String> else mutableListOf()
            val requestedCourses = if (courseIds[REQUESTED_COURSES] != null) courseIds[REQUESTED_COURSES] as MutableList<String> else mutableListOf()
            courses.remove(courseId)
            requestedCourses.remove(courseId)
            usersCollection.document(course.courseTeacher).update(COURSES, course).addOnFailureListener {
                Log.d(TAG, "deleteCourse from teacher: ${it.localizedMessage}")
                return@addOnFailureListener
            }
            usersCollection.document(course.courseTeacher).update(REQUESTED_COURSES, requestedCourses).addOnFailureListener {
                Log.d(TAG, "deleteCourse from teacher: ${it.localizedMessage}")
                return@addOnFailureListener
            }
        }


        // Delete the course from creator
        usersCollection.document(course.courseCreator).get().addOnSuccessListener { courseIds ->
            val courses = if (courseIds[COURSES]  != null) courseIds[COURSES] as MutableList<String> else mutableListOf()
            val requestedCourses = if (courseIds[REQUESTED_COURSES] != null) courseIds[REQUESTED_COURSES] as MutableList<String> else mutableListOf()
            courses.remove(courseId)
            requestedCourses.remove(courseId)
            usersCollection.document(course.courseCreator).update(COURSES, course).addOnFailureListener {
                Log.d(TAG, "deleteCourse from creator: ${it.localizedMessage}")
                return@addOnFailureListener
            }
            usersCollection.document(course.courseCreator).update(REQUESTED_COURSES, requestedCourses).addOnFailureListener {
                Log.d(TAG, "deleteCourse from creator: ${it.localizedMessage}")
                return@addOnFailureListener
            }
        }

        // Delete the course from enrolled students
        course.enrolledStudents.forEach { students ->
            usersCollection.document(students).get().addOnSuccessListener { courseIds ->
                val courses = if (courseIds[COURSES]  != null) courseIds[COURSES] as MutableList<String> else mutableListOf()
                courses.remove(courseId)

                usersCollection.document(students).update(COURSES, course).addOnFailureListener {
                    Log.d(TAG, "deleteCourse from students: ${it.localizedMessage}")
                    return@addOnFailureListener
                }
            }
        }


        // Delete the course classes
        classesCollection.get().addOnSuccessListener { querySnapshot ->
            querySnapshot?.forEach { queryDocumentSnapshot ->
                if (queryDocumentSnapshot != null && queryDocumentSnapshot.id.contains(courseId)) {
                    classesCollection.document(queryDocumentSnapshot.id).delete().addOnFailureListener {
                        Log.d(TAG, "deleteCourse classes: ${it.localizedMessage}")
                        return@addOnFailureListener
                    }
                }
            }
        }

        // Delete the course term tests
        termTestsCollection.get().addOnSuccessListener { querySnapshot ->
            querySnapshot?.forEach { queryDocumentSnapshot ->
                if (queryDocumentSnapshot != null && queryDocumentSnapshot.id.contains(courseId)) {
                    termTestsCollection.document(queryDocumentSnapshot.id).delete().addOnFailureListener {
                        Log.d(TAG, "deleteCourse classes: ${it.localizedMessage}")
                        return@addOnFailureListener
                    }
                }
            }
        }

        // Delete the course assignments
        assignmentsCollection.get().addOnSuccessListener { querySnapshot ->
            querySnapshot?.forEach { queryDocumentSnapshot ->
                if (queryDocumentSnapshot != null && queryDocumentSnapshot.id.contains(courseId)) {
                    assignmentsCollection.document(queryDocumentSnapshot.id).delete().addOnFailureListener {
                        Log.d(TAG, "deleteCourse classes: ${it.localizedMessage}")
                        return@addOnFailureListener
                    }
                }
            }
        }

        /*TODO: Consider deleting course related posts */

        // Delete the course
        coursesCollection.document(courseId).delete().addOnFailureListener {
            Log.d(TAG, "deleteCourse: ${it.localizedMessage}")
        }


    }.catch {
        Log.d(TAG, "deleteCourse: catch block: ${it.localizedMessage}")
    }

    override fun updateClassStatus(details: ClassDetails): Flow<DataState<ClassDetails>> {
        TODO("Not yet implemented")
    }

    override fun deleteClass(classDetails: ClassDetails): Flow<DataState<ClassDetails>> {
        TODO("Not yet implemented")
    }
}
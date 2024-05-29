package com.nasiat_muhib.classmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getCourseFromFirestoreDocument
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getUserFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.ASSIGNMENTS_COLLECTION
import com.nasiat_muhib.classmate.strings.CLASSES_COLLECTION
import com.nasiat_muhib.classmate.strings.COURSES
import com.nasiat_muhib.classmate.strings.COURSES_COLLECTION
import com.nasiat_muhib.classmate.strings.COURSE_CREATOR
import com.nasiat_muhib.classmate.strings.ENROLLED_STUDENTS
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

    override fun createCourse(
        course: Course,
        classDetailsSet: Set<ClassDetails>,
    ): Flow<Pair<DataState<Course>, DataState<List<ClassDetails>>>> = flow {
        emit(Pair(DataState.Loading, DataState.Loading))
        val courseId = "${course.courseDepartment}:${course.courseCode}:${course.courseCreator}"

        // Return If Course Already Exists
        val firestoreCourseDocument = coursesCollection.document(courseId).get().await()
        if (firestoreCourseDocument.data != null) {
            Log.d(TAG, "createCourse: returning because course already exists.")
            return@flow
        }

        Log.d(TAG, "createCourse: returning before doing anything")

        // Send Teacher request
        val teacherDocument = usersCollection.document(course.courseTeacher).get().await()
        val teacher = getUserFromFirestoreDocument(teacherDocument)
        val requestedCourses = mutableSetOf<String>()
        requestedCourses.addAll(teacher.requestedCourses)
        requestedCourses.add(courseId)
        usersCollection.document(course.courseTeacher).update(REQUESTED_COURSES, requestedCourses.toList()).await()


        // Update Creators Course List
        val creatorDocument = usersCollection.document(course.courseCreator).get().await()
        val creator = getUserFromFirestoreDocument(creatorDocument)
        val creatorCourses = mutableSetOf<String>()
        creatorCourses.addAll(creator.courses)
        creatorCourses.add(courseId)
        usersCollection.document(course.courseCreator).update(COURSES, creatorCourses.toList())
            .await()

        // Create Course Classes
        classDetailsSet.forEach { classDetails ->
            val classIndex = classDetailsSet.indexOf(classDetails)
            val classId = "$courseId:$classIndex"
            val details = classDetails.copy(
                classNo = classIndex,
                classCourseCode = course.courseCode,
                classDepartment = course.courseDepartment,
                classCourseCreator = course.courseCreator
            )
//            Log.d(TAG, "createCourse: $details")
            classesCollection.document(classId).set(details.toMap()).await()
        }


        // Create Course
        coursesCollection.document(courseId).set(course.toMap()).await()

        emit(Pair(DataState.Success(course), DataState.Success(classDetailsSet.toList())))

    }
        .catch {
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

    }
        .catch {
            Log.d(TAG, "getRequestedCourses: ${it.localizedMessage}")
        }

    override fun getCreatedCourses(
        courseIds: List<String>,
        creatorEmail: String,
    ): Flow<List<Course>> = callbackFlow {

        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val createdCourses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
                courseIds.forEach {
                    val course = getCourseFromFirestoreDocument(document)
                    if (!course.pendingStatus && document.id.contains(it) && document[COURSE_CREATOR] == creatorEmail) {
                        createdCourses.add(course)
                    }
                }
            }

            trySend(createdCourses).isSuccess

            if (error != null) {
                Log.d(TAG, "getCreatedCourses: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
        Log.d(TAG, "getCreatedCourses: $it")
    }

    override fun getPendingCourseList(
        courseIds: List<String>,
        creatorEmail: String,
    ): Flow<List<Course>> = callbackFlow {

        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val pendingCourses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
                courseIds.forEach {
                    val course = getCourseFromFirestoreDocument(document)
                    if (course.pendingStatus && document.id.contains(it) && document[COURSE_CREATOR] == creatorEmail) {
                        pendingCourses.add(course)
                    }
                }
            }
            trySend(pendingCourses).isSuccess

            if (error != null) {
                Log.d(TAG, "getPendingCourseList: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
        Log.d(TAG, "getPendingCourseList: $it")
    }

    override fun getCourses(courseIds: List<String>): Flow<List<Course>> = callbackFlow {
        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val courses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
                courseIds.forEach {
                    val course = getCourseFromFirestoreDocument(document)
                    if (!course.pendingStatus && document.id.contains(it)) {
                        courses.add(course)
                    }
                }
            }
            trySend(courses).isSuccess

            if (error != null) {
                Log.d(TAG, "getCourses: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
        Log.d(TAG, "getCourses: $it")
    }


    override fun acceptCourse(course: Course): Flow<DataState<Course>> = flow<DataState<Course>> {
        emit(DataState.Loading)

        val courseId = "${course.courseDepartment}:${course.courseCode}:${course.courseCreator}"

        // Change Teacher's request status
        val teacherDocument = usersCollection.document(course.courseCode).get().await()
        if (teacherDocument.exists() && teacherDocument != null) {
            val teacher = getUserFromFirestoreDocument(teacherDocument)
            val requestedCourses = mutableSetOf<String>()
            val courses = mutableSetOf<String>()
            requestedCourses.addAll(teacher.requestedCourses)
            courses.addAll(teacher.courses)
            requestedCourses.remove(courseId)
            courses.add(courseId)

            usersCollection.document(course.courseTeacher).update(COURSES, courses.toList()).await()
            usersCollection.document(course.courseTeacher)
                .update(REQUESTED_COURSES, requestedCourses.toList()).await()
        } else {
            Log.d(TAG, "acceptCourse: teacher does not exists")
            return@flow
        }

        // Change course pending status
        coursesCollection.document(courseId).update(PENDING_STATUS, false).await()
    }.catch {
        Log.d(TAG, "acceptCourse: ${it.localizedMessage}")
    }

    override fun deleteCourse(course: Course): Flow<DataState<Course>> = flow<DataState<Course>> {
        emit(DataState.Loading)

        val courseId = "${course.courseDepartment}:${course.courseCode}:${course.courseCreator}"

        // Delete the course from teacher
        val teacherDocument = usersCollection.document(course.courseTeacher).get().await()
        val teacher = getUserFromFirestoreDocument(teacherDocument)
        val requestedCourses = mutableSetOf<String>()
        val courses = mutableSetOf<String>()
        requestedCourses.addAll(teacher.requestedCourses)
        courses.addAll(teacher.courses)
        requestedCourses.remove(courseId)
        courses.remove(courseId)

        usersCollection.document(course.courseTeacher).update(COURSES, courses.toList()).await()
        usersCollection.document(course.courseTeacher).update(REQUESTED_COURSES, requestedCourses.toList()).await()


        // Delete the course from creator
        val creatorDocument = usersCollection.document(course.courseCreator).get().await()
        val creator = getUserFromFirestoreDocument(creatorDocument)
        val createdCourses = mutableSetOf<String>()
        createdCourses.addAll(creator.courses)
        createdCourses.remove(courseId)
        usersCollection.document(course.courseCreator).update(COURSES, createdCourses.toList()).await()

        // Delete the course from enrolled students
        course.enrolledStudents.forEach { student ->
            val enrolledStudentDocument = usersCollection.document(student).get().await()
            val enrolledStudent = getUserFromFirestoreDocument(enrolledStudentDocument)
            val enrolledCourses = mutableSetOf<String>()
            enrolledCourses.addAll(enrolledStudent.courses)
            enrolledCourses.remove(courseId)
            usersCollection.document(student).update(COURSES, enrolledCourses.toList()).await()
        }


        // Delete the course classes
        val allClasses = classesCollection.get().await()
        allClasses?.forEach { queryDocumentSnapshot ->
            if (queryDocumentSnapshot != null && queryDocumentSnapshot.id.contains(courseId)) {
                classesCollection.document(queryDocumentSnapshot.id).delete().await()
            }
        }

        // Delete the course term tests
        val allTermTest = termTestsCollection.get().await()
        allTermTest?.forEach { queryDocumentSnapshot ->
            if (queryDocumentSnapshot != null && queryDocumentSnapshot.id.contains(courseId)) {
                termTestsCollection.document(queryDocumentSnapshot.id).delete().await()
            }

        }

        // Delete the course assignments
        val allAssignments = assignmentsCollection.get().await()
        allAssignments?.forEach { queryDocumentSnapshot ->
            if (queryDocumentSnapshot != null && queryDocumentSnapshot.id.contains(courseId)) {
                assignmentsCollection.document(queryDocumentSnapshot.id).delete().await()
            }

        }

        /*TODO: Consider deleting course related posts */

        // Delete the course
        coursesCollection.document(courseId).delete().await()


    }.catch {
        Log.d(TAG, "deleteCourse: ${it.localizedMessage}")
    }

    override fun enrollCourse(courseId: String, email: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)

        // Update The User's Course List
        val userDocument = usersCollection.document(email).get().await()
        val user = getUserFromFirestoreDocument(userDocument)
        val enrolledCourses = mutableSetOf<String>()
        enrolledCourses.addAll(user.courses)
        enrolledCourses.add(courseId)
        usersCollection.document(email).update(COURSES, enrolledCourses.toList()).await()

        // Update The Course's enrolled students list
        val courseDocument = coursesCollection.document(courseId).get().await()
        val course = getCourseFromFirestoreDocument(courseDocument)
        val enrolledStudents = mutableSetOf<String>()
        enrolledStudents.addAll(course.enrolledStudents)
        enrolledStudents.add(email)
        coursesCollection.document(courseId).update(ENROLLED_STUDENTS, enrolledStudents.toList()).await()

        emit(DataState.Success(true))

    }.catch {
        emit(DataState.Error(it.localizedMessage!!))
        Log.d(TAG, "enrollCourse: ${it.localizedMessage}")
    }

    override fun leaveCourse(courseId: String, email: String): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)

            // Update The User's Course List
            val userDocument = usersCollection.document(email).get().await()
            val user = getUserFromFirestoreDocument(userDocument)
            val enrolledCourses = mutableSetOf<String>()
            enrolledCourses.addAll(user.courses)
            enrolledCourses.remove(courseId)
            usersCollection.document(email).update(COURSES, enrolledCourses.toList()).await()

            // Update The Course's enrolled students list
            val courseDocument = coursesCollection.document(courseId).get().await()
            val course = getCourseFromFirestoreDocument(courseDocument)
            val enrolledStudents = mutableSetOf<String>()
            enrolledStudents.addAll(course.enrolledStudents)
            enrolledStudents.remove(email)
            coursesCollection.document(courseId).update(ENROLLED_STUDENTS, enrolledStudents.toList()).await()

            emit(DataState.Success(true))

        }.catch {
            Log.d(TAG, "leaveCourse: ${it.localizedMessage}")
        }

    companion object {
        const val TAG = "CourseRepositoryImpl"
    }

}
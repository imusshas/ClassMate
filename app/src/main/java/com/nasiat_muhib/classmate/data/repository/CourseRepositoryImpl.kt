package com.nasiat_muhib.classmate.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.Constants.CLASS_DETAILS
import com.nasiat_muhib.classmate.core.Constants.COURSES_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.TEACHER
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    firestoreRef: FirebaseFirestore
): CourseRepository {

    private val  coursesCollection = firestoreRef.collection(COURSES_COLLECTION)
    override suspend fun createCourse(course: Course): Flow<ResponseState<Course>> = flow {
        var isSuccessful = false
        emit(ResponseState.Loading)
        try {
            val courseDocument = coursesCollection.document(course.code)
            courseDocument.get().addOnSuccessListener {
                if(it.exists()) {
                    coursesCollection.document().update(course.toMap()).addOnSuccessListener {
                        isSuccessful = true
                    }
                } else {
                    coursesCollection.document().set(course.toMap()).addOnSuccessListener {
                        isSuccessful = true
                    }
                }
            }.await()

            if(isSuccessful) {
                emit(ResponseState.Success(course))
            }
        }catch (e: Exception) {
            emit(ResponseState.Failure(e.message.toString()))
        }
    }

    override suspend fun updateCourse(course: Course): Flow<ResponseState<Course>> = flow {
        var isSuccessful = false
        emit(ResponseState.Loading)
        try {
            val courseDocument = coursesCollection.document(course.code)
            if(course.teacher.isNotEmpty()) {
                isSuccessful = false
                courseDocument.update(mapOf(TEACHER to course.teacher)).addOnSuccessListener {
                    isSuccessful = true
                }.await()
            }

            if(course.classDetails.isNotEmpty()) {
                isSuccessful = false
                courseDocument.update(mapOf(CLASS_DETAILS to course.classDetails)).addOnSuccessListener {
                    isSuccessful = true
                }.await()
            }

            if(isSuccessful) {
                emit(ResponseState.Success(course))
            }
        } catch (e: Exception) {
            emit(ResponseState.Failure(e.message.toString()))
        }
    }

    override suspend fun deleteCourse(course: Course): Flow<ResponseState<Course>> = flow {
        var isSuccessful = false
        emit(ResponseState.Loading)

        try {
            val courseDocument = coursesCollection.document(course.code)
            courseDocument.delete().addOnSuccessListener {
                isSuccessful = true
            }.await()
            emit(ResponseState.Success(course))
        } catch (e: Exception) {
            emit(ResponseState.Failure(e.message.toString()))
        }
    }
}
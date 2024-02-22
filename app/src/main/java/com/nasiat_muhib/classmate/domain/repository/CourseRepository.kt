package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.model.ResponseState
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    suspend fun createCourse(course: Course): Flow<ResponseState<Course>>

    suspend fun updateCourse(course: Course): Flow<ResponseState<Course>>

    suspend fun deleteCourse(course: Course): Flow<ResponseState<Course>>
}
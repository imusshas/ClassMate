package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.model.ResponseState
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    fun createCourse(course: Course): Flow<ResponseState<Course>>

    fun updateCourse(course: Course): Flow<ResponseState<Course>>

    fun deleteCourse(course: Course): Flow<ResponseState<Course>>

    fun getCourse(courseCode: String): Flow<ResponseState<Course>>
}
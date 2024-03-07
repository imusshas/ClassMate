package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun createCourse(course: Course, classDetailsSet: Set<ClassDetails>): Flow<Pair<DataState<Course>, DataState<Set<ClassDetails>>>>
    fun getCourse(courseId: String): Flow<DataState<Course>>

    fun getCourseList(courseIds: List<String>): Flow<List<Course>>
    fun updateCourse(): Flow<DataState<Course>>
    fun deleteCourse(): Flow<DataState<Course>>
}
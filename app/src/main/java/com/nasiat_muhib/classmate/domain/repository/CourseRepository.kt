package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun createCourse(course: Course, classDetailsSet: Set<ClassDetails>): Flow<Pair<DataState<Course>, DataState<List<ClassDetails>>>>
    fun getRequestedCourses(courseIds: List<String>): Flow<List<Course>>
    fun getCreatedCourses(courseIds: List<String>, creatorEmail: String): Flow<List<Course>>
    fun getPendingCourseList(courseIds: List<String>): Flow<List<Course>>

    // Enrolled Courses
    fun getCourses(courseIds: List<String>): Flow<List<Course>>
    fun acceptCourse(course: Course): Flow<DataState<Course>>
    fun deleteCourse(course: Course): Flow<DataState<Course>>
    fun  enrollCourse(courseId: String, email: String): Flow<DataState<Boolean>>
    fun leaveCourse(courseId: String, email: String): Flow<DataState<Boolean>>
    fun updateClassStatus(details: ClassDetails): Flow<DataState<ClassDetails>>

}
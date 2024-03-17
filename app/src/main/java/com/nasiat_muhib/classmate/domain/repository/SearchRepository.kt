package com.nasiat_muhib.classmate.domain.repository


import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getAllTeachers(): Flow<Set<User>>
    fun getAllCourses(): Flow<Set<Course>>
    fun getAllClasses(): Flow<Set<ClassDetails>>
}
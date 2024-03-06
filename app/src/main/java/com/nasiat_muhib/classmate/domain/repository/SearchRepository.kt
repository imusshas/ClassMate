package com.nasiat_muhib.classmate.domain.repository


import com.nasiat_muhib.classmate.data.model.User
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getAllTeachers(): Flow<Set<User>>

    fun getAllCourses(): Flow<Set<User>>
}
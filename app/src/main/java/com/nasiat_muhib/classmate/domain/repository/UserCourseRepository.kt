package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.model.DataOrException
import kotlinx.coroutines.flow.Flow

interface UserCourseRepository {

    fun getUserCourseList(coursesList: List<String>): Flow<DataOrException<List<Course>>>


}
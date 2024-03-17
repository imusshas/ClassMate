package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface ClassDetailsRepository {

    fun createClass(classDetails: ClassDetails): Flow<DataState<ClassDetails>>

    fun deleteClass(classDetails: ClassDetails): Flow<DataState<ClassDetails>>

    fun getClassesForSingleCourse(courseId: String): Flow<List<ClassDetails>>

    fun getClassesForMultipleCourse(courseIds: List<String>): Flow<List<ClassDetails>>

    fun changeActiveStatus(classDetails: ClassDetails, status: Boolean): Flow<DataState<ClassDetails>>
}
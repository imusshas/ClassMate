package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.model.ResponseState
import kotlinx.coroutines.flow.Flow

interface ClassDetailsRepository {

    fun createClassDetailsList(courseCode: String, classDetailsList: List<ClassDetails>): Flow<ResponseState<List<ClassDetails>>>

    fun updateClassDetailsList(courseCode: String, classDetailsList: List<ClassDetails>): Flow<ResponseState<List<ClassDetails>>>

    fun deleteClassDetailsList(courseCode: String): Flow<ResponseState<List<ClassDetails>>>

    fun getClassDetailsList(courseCode: String): Flow<ResponseState<List<ClassDetails>>>
}
package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.Event
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun createTermTest (event: Event): Flow<DataState<Event>>

    fun getTermTestList (courseId: String): Flow<List<Event>>

    fun deleteTermTest (event: Event): Flow<DataState<Event>>

    fun createAssignment (event: Event): Flow<DataState<Event>>

    fun getAssignmentList (courseId: String): Flow<List<Event>>

    fun deleteAssignment (event: Event): Flow<DataState<Event>>
}
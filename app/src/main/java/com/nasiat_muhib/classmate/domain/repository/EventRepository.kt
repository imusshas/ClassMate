package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.Event
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun createEvent (event: Event): Flow<DataState<Event>>

    fun getEventList (courseId: String, whichEvent: String): Flow<List<Event>>

    fun deleteEvent (event: Event): Flow<DataState<Event>>

}
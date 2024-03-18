package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.ResourceLink
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface ResourceLinkRepository {

    fun createResource(resourceLink: ResourceLink): Flow<DataState<ResourceLink>>
    fun getResources(courseId: String): Flow<List<ResourceLink>>
    fun deleteResource(resourceLink: ResourceLink): Flow<DataState<ResourceLink>>

}
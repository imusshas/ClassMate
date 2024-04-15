package com.nasiat_muhib.classmate.domain.repository

import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun updateToken(token: String): Flow<Boolean>
    fun getToken(): Flow<String>
    fun sendUpdateNotification(): Flow<Boolean>
    fun sendEventNotification(): Flow<Boolean>
    fun sendClassCancelNotification(
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String
    ): Flow<Boolean>
}
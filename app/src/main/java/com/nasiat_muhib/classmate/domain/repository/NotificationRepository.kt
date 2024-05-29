package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun updateToken(): Flow<Boolean>
    fun getToken(email: String): Flow<String>
    fun sendUpdateNotification(
        courseCreator: String,
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean>
    fun sendTermTestNotification(
        courseCreator: String,
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean>

    fun sendAssignmentNotification(
        courseCreator: String,
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean>
    fun sendClassCancelNotification(
        courseCreator: String,
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String
    ): Flow<Boolean>

    fun getNotifications(email: String): Flow<List<Notification>>
}
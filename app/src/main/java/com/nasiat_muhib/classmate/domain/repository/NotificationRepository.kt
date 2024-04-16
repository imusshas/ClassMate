package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun updateToken(token: String): Flow<Boolean>
    fun getToken(email: String): Flow<String>
    fun sendUpdateNotification(
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean>
    fun sendTermTestNotification(
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean>

    fun sendAssignmentNotification(
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String,
    ): Flow<Boolean>
    fun sendClassCancelNotification(
        courseDepartment: String,
        courseCode: String,
        courseTitle: String,
        courseUsers: List<String>,
        token: String
    ): Flow<Boolean>

    fun getNotifications(email: String): Flow<List<Notification>>
}
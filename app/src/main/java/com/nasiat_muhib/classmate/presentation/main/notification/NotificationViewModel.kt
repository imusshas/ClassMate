package com.nasiat_muhib.classmate.presentation.main.notification

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nasiat_muhib.classmate.MainActivity
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.Notification
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.NotificationRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val notificationRepo: NotificationRepository,
    private val courseRepo: CourseRepository,
    private val auth: FirebaseAuth
): ViewModel() {

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Loading)
    val userState = _userState.asStateFlow()

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications = _notifications.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()


    fun getUser() = viewModelScope.launch {
        auth.currentUser?.email?.let {email ->
            userRepo.getCurrentUser(email).collectLatest {
                _userState.value = it
            }
        }
    }

    fun getNotifications(email: String) = viewModelScope.launch {
        notificationRepo.getNotifications(email).collectLatest { notificationList ->
            _notifications.value = notificationList.sortedByDescending {
                it.creationTime
            }
        }
    }

    fun getCourses(courseIds: List<String>) = viewModelScope.launch {
        courseRepo.getCourses(courseIds).collectLatest {
            _courses.value = it
        }
    }
}
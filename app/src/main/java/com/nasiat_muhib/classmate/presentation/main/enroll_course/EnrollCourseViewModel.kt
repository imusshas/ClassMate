package com.nasiat_muhib.classmate.presentation.main.enroll_course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.EnrollCourseUIEvent
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnrollCourseViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val courseRepo: CourseRepository,
) : ViewModel() {

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val userState = _userState.asStateFlow()

    private val _alreadyEnrolled = MutableStateFlow<List<Course>>(listOf())
    val alreadyEnrolled = _alreadyEnrolled.asStateFlow()


    init {
        val currentUser = userRepo.currentUser
        if (currentUser?.email != null) {
            getUser(currentUser.email!!)
        }
    }

    private fun getUser(email: String) = viewModelScope.launch {
        userRepo.getUser(email).collectLatest {
            _userState.value = it
        }
    }


    fun onEnrollCourseEvent(event: EnrollCourseUIEvent) {
        when (event) {
            is EnrollCourseUIEvent.DisplayCourseSwipe -> {
                ClassMateAppRouter.navigateTo(
                    Screen.CourseDetailsDisplay(
                        event.course,
                        event.screen
                    )
                )
            }

            is EnrollCourseUIEvent.LeaveCourseSwipe -> {
                leaveCourse(event.courseId)
            }
        }
    }


    private fun leaveCourse(courseId: String) = viewModelScope.launch {
        userState.value.data?.let { user ->
            courseRepo.leaveCourse(courseId, user.email).collectLatest {

            }
        }
    }


    fun getAlreadyEnrolledCourses() = viewModelScope.launch {
        userState.value.data?.let { user ->
            courseRepo.getCourses(user.courses).collectLatest { list ->
                val courseList = mutableListOf<Course>()
                list.forEach {
                    if (it.courseTeacher != user.email && it.courseCreator != user.email) {
                        courseList.add(it)
                    }
                }
                _alreadyEnrolled.value = courseList
            }
        }
    }

}
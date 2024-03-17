package com.nasiat_muhib.classmate.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.HomeUIEvent
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.domain.repository.ClassDetailsRepository
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val courseRepo: CourseRepository,
    private val authRepo: AuthenticationRepository,
    private val classDetailsRepo: ClassDetailsRepository,
) : ViewModel() {

    private val currentUser = userRepo.currentUser

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val userState = _userState.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()

    private val _requestedCourses = MutableStateFlow<List<Course>>(emptyList())
    val requestedCourses = _requestedCourses.asStateFlow()

    private val _classes = MutableStateFlow<List<ClassDetails>>(emptyList())
    val classes = _classes.asStateFlow()

    private val _todayClasses = MutableStateFlow<List<ClassDetails>>(emptyList())
    val todayClasses = _todayClasses.asStateFlow()

    private val _tomorrowClasses = MutableStateFlow<List<ClassDetails>>(emptyList())
    val tomorrowClasses = _tomorrowClasses.asStateFlow()


    init {
        if (currentUser.email != null) {
            getUser(currentUser.email!!)
        }
    }


    private fun getUser(email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.getUser(email).collectLatest {
            _userState.value = it
//            Log.d(TAG, "getUser: $email: ${it.data}")
        }
    }

    fun signOut() = viewModelScope.launch {
        authRepo.signOut().collectLatest {

        }

//        Log.d(TAG, "signOut: ${currentUser.email}")
    }


    fun getCourseList(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        courseRepo.getCourses(courseIds).collectLatest {
            _courses.value = it
        }
    }


    fun getRequestedCourseList(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        courseRepo.getRequestedCourses(courseIds).collectLatest {
            _requestedCourses.value = it
        }
    }

    fun getClassDetails(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        classDetailsRepo.getClassesForMultipleCourse(courseIds).collectLatest {
            _classes.value = it
        }
    }

    fun getTodayAndTomorrowClassesClasses() = viewModelScope.launch(Dispatchers.IO) {

        val todayClassList = mutableListOf<ClassDetails>()
        val today = LocalDate.now().dayOfWeek.toString()

        val tomorrowClassList = mutableListOf<ClassDetails>()
        val tomorrow = LocalDate.now().dayOfWeek.plus(1).toString()

        classes.value.forEach {
            if (today.contains(it.weekDay, ignoreCase = true)) {
                todayClassList.add(it)
            }
            if (tomorrow.contains(it.weekDay, ignoreCase = true)) {
                tomorrowClassList.add(it)
            }
        }
        _todayClasses.value = todayClassList
        _tomorrowClasses.value = tomorrowClassList
    }


    fun onHomeEvent(event: HomeUIEvent) {

        when (event) {
            is HomeUIEvent.ClassStatusChange -> {
                userState.value.data?.let { user ->
                    courses.value.forEach { course ->
                        if (
                            event.classDetails.classDepartment == course.courseDepartment &&
                            event.classDetails.classCourseCode == course.courseCode &&
                            (course.courseTeacher == user.email || course.courseCreator == user.email)
                        ) {
                            Log.d(TAG, "onHomeEvent: $course")
                            changeActiveStatus(event.classDetails, event.activeStatus)
                            getTodayAndTomorrowClassesClasses()
                        }

                    }

                }
            }

            is HomeUIEvent.AcceptCourseRequest -> {
                acceptCourseRequest(event.course)
            }

            is HomeUIEvent.DeleteCourseRequest -> {
                deleteCourseRequest(event.course)
            }

            is HomeUIEvent.DisplayCourse -> {
                ClassMateAppRouter.navigateTo(
                    Screen.CourseDetailsDisplay(
                        event.course,
                        event.screen
                    )
                )
            }
        }
    }


    private fun deleteCourseRequest(course: Course) = viewModelScope.launch {
        courseRepo.deleteCourse(course).collectLatest {

        }
    }

    private fun acceptCourseRequest(course: Course) = viewModelScope.launch {
        courseRepo.acceptCourse(course).collectLatest {

        }
    }


    private fun changeActiveStatus(classDetails: ClassDetails, status: Boolean) =
        viewModelScope.launch {
            classDetailsRepo.changeActiveStatus(classDetails, status).collectLatest {

            }
        }

    companion object {
        const val TAG = "HomeScreenViewModel"
    }
}
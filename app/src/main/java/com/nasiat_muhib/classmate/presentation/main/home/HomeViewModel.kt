package com.nasiat_muhib.classmate.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.type.DateTime
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.HomeUIEvent
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val courseRepo: CourseRepository,
) : ViewModel() {

    private val currentUser = userRepo.currentUser

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val userState = _userState.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()

    private val _requestedCourses = MutableStateFlow<List<Course>>(emptyList())
    val requestedCourses = _requestedCourses.asStateFlow()

    private val _classes = MutableStateFlow<Map<String, List<ClassDetails>>>(emptyMap())
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
        }
    }


    fun getCourseList(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        courseRepo.getCourseList(courseIds).collectLatest {
            _courses.value = it
        }
    }


    fun getRequestedCourseList(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        courseRepo.getCourseList(courseIds).collectLatest {
            _requestedCourses.value = it
        }
    }

    fun getClassDetails() = viewModelScope.launch(Dispatchers.IO) {

        val classesList: MutableMap<String, List<ClassDetails>> = mutableMapOf()
        courses.value.forEach { course ->
            val id = "${course.courseDepartment}:${course.courseCode}"

            courseRepo.getClassDetailsList(id).collectLatest {
                classesList[id] = it
            }
        }
        _classes.value = classesList
    }

    fun getTodayClasses() = viewModelScope.launch (Dispatchers.IO) {

        val todayClassList = mutableListOf<ClassDetails>()
        classes.value.values.forEach { details ->
            details.forEach {
                if (it.weekDay.uppercase(Locale.ROOT) == LocalDate.now().dayOfWeek.toString()) {
                    todayClassList.add(it)
                }
            }
        }

        _todayClasses.value = todayClassList

    }


    fun getTomorrowClasses() = viewModelScope.launch (Dispatchers.IO) {

        val tomorrowClassList = mutableListOf<ClassDetails>()
        classes.value.values.forEach { details ->
            details.forEach {
                if (it.weekDay.uppercase(Locale.ROOT) == LocalDate.now().plusDays(1).dayOfWeek.toString()) {
                    tomorrowClassList.add(it)
                }
            }
        }
        _tomorrowClasses.value = tomorrowClassList
    }


    fun onHomeEvent(event: HomeUIEvent) {

        when (event) {
            is HomeUIEvent.ClassStatusChange -> {
                todayClasses.value.forEach {
                    if (it == event.classDetails) {
                        if (it.isActive != event.isActive) {
                            val details = it.copy(
                                isActive = event.isActive
                            )
                            courseRepo.updateClass(details)
                        }
                    }
                }

                tomorrowClasses.value.forEach {
                    if (it == event.classDetails) {
                        if (it.isActive != event.isActive) {
                            val details = it.copy(
                                isActive = event.isActive
                            )
                            courseRepo.updateClass(details)
                        }
                    }
                }
            }
            HomeUIEvent.TodayClassClicked -> TODO()
            HomeUIEvent.TomorrowClassClicked -> TODO()
        }
    }

    companion object {
        const val TAG = "HomeScreenViewModel"
    }
}
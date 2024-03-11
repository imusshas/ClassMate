package com.nasiat_muhib.classmate.presentation.main.create_semester

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.CreateSemesterUIEvent
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
import javax.inject.Inject

@HiltViewModel
class CreateSemesterViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val courseRepo: CourseRepository,
) : ViewModel() {

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val userState = _userState.asStateFlow()


    private val _courses = MutableStateFlow<List<Course>>(listOf())
    val courses = _courses.asStateFlow()

    private val _pendingCourses = MutableStateFlow<List<Course>>(listOf())
    val pendingCourses = _pendingCourses.asStateFlow()

    fun onCreateSemesterEvent(event: CreateSemesterUIEvent) {

        when (event) {
            CreateSemesterUIEvent.CreateSemesterFABClick -> {
                ClassMateAppRouter.navigateTo(Screen.CreateCourse)
            }

            is CreateSemesterUIEvent.EditCourseUIEvent -> {

            }

            is CreateSemesterUIEvent.DeleteCourseSwipe -> {
                deleteCourse(event.course)
            }
        }

    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUser(userRepo.currentUser.email!!)
        }
    }

    private fun getUser(email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.getUser(email).collect {
            _userState.value = it
//            Log.d(TAG, "getUser: $it")
        }
    }

    fun getCourses(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {

//        Log.d(TAG, "getCourses: courseIds: $courseIds")
        if (courseIds.isNotEmpty()) {
            try {
                courseRepo.getCourseList(courseIds).collectLatest {
                    _courses.value = it
//                    Log.d(TAG, "getCourses: $it")
                }
            } catch (e: Exception) {
                Log.d(TAG, "getCourses: ${e.localizedMessage}")
            }
        }
    }

    fun getPendingCourses(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        if (courseIds.isNotEmpty()) {
            try {
                courseRepo.getPendingCourseList(courseIds).collectLatest {
                    _pendingCourses.value = it
                }
            } catch (e: Exception) {
                Log.d(TAG, "getPendingCourses: ${e.localizedMessage}")
            }
        }
    }

    private fun deleteCourse(course: Course) = viewModelScope.launch(Dispatchers.IO) {
        courseRepo
            .deleteCourse(course)
            .collectLatest {

            }
    }



    private fun validateAllStates() {

    }

    companion object {
        private const val TAG = "CreateSemesterViewModel"
    }
}
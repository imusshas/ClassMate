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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class CreateSemesterViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val courseRepo: CourseRepository,
) : ViewModel() {

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val userState = _userState.asStateFlow()


    private val _courses = MutableStateFlow<List<Course>>(listOf())
    val courses = _courses.asStateFlow()

    fun onCreateSemesterEvent(createSemester: CreateSemesterUIEvent) {

        when (createSemester) {
            CreateSemesterUIEvent.CreateSemesterFABClick -> {
                ClassMateAppRouter.navigateTo(Screen.CreateCourse)
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


    private val courseList = mutableListOf<Course>()
    fun getCourses(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {

        Log.d(TAG, "getCourses: courseIds: $courseIds")
        if (courseIds.isNotEmpty()) {
            courseRepo.getCourseList(courseIds).collectLatest {
                _courses.value = it
                Log.d(TAG, "getCourses: $it")
            }
        }
    }

    private fun validateAllStates() {

    }

    companion object {
        private const val TAG = "CreateSemesterViewModel"
    }
}
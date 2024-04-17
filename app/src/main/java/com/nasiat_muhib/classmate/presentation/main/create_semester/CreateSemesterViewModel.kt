package com.nasiat_muhib.classmate.presentation.main.create_semester

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.CreateSemesterUIEvent
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.state.DataState
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
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val userState = _userState.asStateFlow()


    private val _createdCourses = MutableStateFlow<List<Course>>(listOf())
    val cratedCourses = _createdCourses.asStateFlow()

    private val _pendingCourses = MutableStateFlow<List<Course>>(listOf())
    val pendingCourses = _pendingCourses.asStateFlow()

    fun onCreateSemesterEvent(event: CreateSemesterUIEvent) {

        when (event) {
            is CreateSemesterUIEvent.DeleteCourseSwipe -> {
                deleteCourse(event.course)
            }
        }

    }

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        auth.currentUser?.email?.let {email ->
            userRepo.getCurrentUser( email).collect {
                _userState.value = it
            }
        }
    }

    fun getCreatedCourses(courses: List<String>, email: String) = viewModelScope.launch {
        courseRepo.getCreatedCourses(courses, email).collectLatest {
            _createdCourses.value = it
        }
    }

    fun getPendingCourses(courses: List<String>, email: String) = viewModelScope.launch {
        courseRepo.getPendingCourseList(courses, email).collectLatest {
            _pendingCourses.value = it
        }
    }

    private fun deleteCourse(course: Course) = viewModelScope.launch(Dispatchers.IO) {
        courseRepo
            .deleteCourse(course)
            .collectLatest {

            }
    }

    companion object {
        private const val TAG = "CreateSemesterViewModel"
    }
}
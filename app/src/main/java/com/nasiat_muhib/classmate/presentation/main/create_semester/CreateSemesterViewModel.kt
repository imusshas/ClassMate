package com.nasiat_muhib.classmate.presentation.main.create_semester

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.ClassDetailsRepository
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSemesterViewModel @Inject constructor(
    private val courseRepo: CourseRepository,
    private val classDetailsRepo: ClassDetailsRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _courseState =
        MutableStateFlow<ResponseState<Course>>(ResponseState.Success(Course()))
    val courseState = _courseState.asStateFlow()

    private val _createdCourseListState = MutableStateFlow<ResponseState<List<Course>>>(ResponseState.Success(listOf()))
    val createdCourseListState = _createdCourseListState.asStateFlow()

    private val _classDetailsState =
        MutableStateFlow<ResponseState<List<ClassDetails>>>(ResponseState.Success(listOf()))
    val classDetailsState = _classDetailsState.asStateFlow()

    init {
        viewModelScope.launch {
            if (auth.currentUser != null) {
                courseRepo.getCreatedCourseList(auth.currentUser?.email!!).collect {
                    _createdCourseListState.value = it
                }
            }
        }
    }

    fun createCourse(course: Course) = viewModelScope.launch {
        courseRepo.createCourse(course).collect {
            _courseState.value = it
        }
    }

    fun updateCourse(course: Course) = viewModelScope.launch {
        courseRepo.updateCourse(course).collect {
            _courseState.value = it
        }
    }

    fun getCourse(courseCode: String) = viewModelScope.launch {
        courseRepo.getCourse(courseCode).collect {
            _courseState.value = it
        }
    }

    fun createClassDetails(courseCode: String, classDetailsList: List<ClassDetails>) = viewModelScope.launch {
        classDetailsRepo.createClassDetailsList(courseCode, classDetailsList).collect {
            _classDetailsState.value = it
        }
    }

    fun updateClassDetails(courseCode: String, classDetailsList: List<ClassDetails>) = viewModelScope.launch {
        classDetailsRepo.updateClassDetailsList(courseCode, classDetailsList).collect {
            _classDetailsState.value = it
        }
    }

    fun getClassDetails(courseCode: String) = viewModelScope.launch {
        classDetailsRepo.getClassDetailsList(courseCode).collect {
            _classDetailsState.value = it
        }
    }
}
package com.nasiat_muhib.classmate.presentation.main.enroll_course.components

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.SearchCourseUIEvent
import com.nasiat_muhib.classmate.domain.event.SearchUIEvent
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.SearchRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SearchCourseViewModel @Inject constructor(
    private val searchRepo: SearchRepository,
    private val courseRepo: CourseRepository,
    private val userRepo: UserRepository,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _courses = MutableStateFlow<Set<Course>>(emptySet())

    @OptIn(FlowPreview::class)
    val courses = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_courses) { text, usersList ->
            if (text.isBlank()) {
                usersList
            } else {
                usersList.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _courses.value
        )


    fun onSearchCourseEvent(event: SearchCourseUIEvent) {
        when (event) {
            is SearchCourseUIEvent.EnrollButtonClicked -> {
                enrollCourse(event.courseId, event.email)
            }
        }
    }

    fun onSearch(event: SearchUIEvent) {
        when (event) {
            is SearchUIEvent.SearchTextChanged -> {
                _searchText.value = event.searchText
            }
        }
    }

    fun getAllCourses(user: User) = viewModelScope.launch(Dispatchers.IO) {
        searchRepo.getAllCourses().collectLatest { courseSet ->
            val searchableCourse = mutableSetOf<Course>()
            courseSet.forEach { course ->
                val courseId = "${course.courseDepartment}:${course.courseCode}"
                if (course.courseCreator != user.email && course.courseTeacher != user.role && !user.courses.contains(courseId)) {
                    searchableCourse.add(course)
                }
            }

            _courses.value = searchableCourse
        }

    }

    private fun enrollCourse(courseId: String, email: String) = viewModelScope.launch {

        courseRepo.enrollCourse(courseId, email).collectLatest {

        }
    }

    companion object {
        const val TAG = "SearchCourseViewModel"
    }
}
package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.lifecycle.ViewModel
import com.nasiat_muhib.classmate.data.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CourseDetailsDisplayViewModel @Inject constructor(

): ViewModel() {

    private val _currentCourse = MutableStateFlow(Course())
    val currentCourse = _currentCourse.asStateFlow()


    fun setCurrentCourse(course: Course) {
        _currentCourse.value = course
    }
}
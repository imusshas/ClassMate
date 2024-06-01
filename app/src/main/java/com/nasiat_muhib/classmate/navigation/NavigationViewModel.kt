package com.nasiat_muhib.classmate.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


class NavigationViewModel: ViewModel() {

    private val _course = MutableStateFlow(Course())
    val course = _course.asStateFlow()

    fun setCourse(course: Course) {
        _course.value = course
        Log.d(TAG, "setCourse: $course")
    }

    companion object {
        const val TAG = "NavigationViewModel"
    }
}
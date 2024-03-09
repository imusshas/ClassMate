package com.nasiat_muhib.classmate.domain.event

sealed class EnrollCourseUIEvent {
    data class EnrollClicked(val courseId: String): EnrollCourseUIEvent()
}
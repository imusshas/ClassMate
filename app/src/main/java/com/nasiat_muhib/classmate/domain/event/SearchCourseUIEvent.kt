package com.nasiat_muhib.classmate.domain.event

sealed class SearchCourseUIEvent {

    data class EnrollButtonClicked(val courseId: String): SearchCourseUIEvent()
}
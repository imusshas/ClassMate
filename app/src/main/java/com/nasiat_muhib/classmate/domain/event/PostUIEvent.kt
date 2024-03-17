package com.nasiat_muhib.classmate.domain.event

import java.sql.Timestamp

sealed class PostUIEvent {

    data class CourseCodeChanged(val courseCode: String) : PostUIEvent()
    data class DescriptionChanged(val description: String) : PostUIEvent()
    data object DiscardButtonClicked : PostUIEvent()
    data class PostButtonClicked(
        val timestamp: Timestamp,
        val creator: String,
        val firstName: String,
        val lastName: String,
    ) : PostUIEvent()
}
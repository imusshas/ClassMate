package com.nasiat_muhib.classmate.domain.event

import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course

sealed class CreateSemesterUIEvent {
    data object CreateSemesterFABClick: CreateSemesterUIEvent()

    data class EditCourseUIEvent(
        val course: Course,
        val classDetailsList: List<ClassDetails>
    ): CreateSemesterUIEvent()
}
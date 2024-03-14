package com.nasiat_muhib.classmate.domain.event

import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course

sealed class HomeUIEvent {

    data class ClassStatusChange(val classDetails: ClassDetails, val isActive: Boolean): HomeUIEvent()
    data class AcceptCourseRequest(val course: Course): HomeUIEvent()

    data class DeleteCourseRequest(val course: Course): HomeUIEvent()
}
package com.nasiat_muhib.classmate.domain.event

import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Event

sealed class CourseDetailsDisplayUIEvent {
    data object ClassTitleClicked: CourseDetailsDisplayUIEvent()
    data class ClassDeleteSwipe(val classDetails: ClassDetails): CourseDetailsDisplayUIEvent()

    data object TermTestTitleClicked : CourseDetailsDisplayUIEvent()
    data class EventDeleteSwipe(val event: Event): CourseDetailsDisplayUIEvent()

    data object AssignmentTitleClicked: CourseDetailsDisplayUIEvent()

    data object ResourceTitleClicked: CourseDetailsDisplayUIEvent()
}
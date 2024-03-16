package com.nasiat_muhib.classmate.domain.event

import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Event
import com.nasiat_muhib.classmate.navigation.Screen

sealed class CourseDetailsDisplayUIEvent {
    data class CourseDetailsDisplayTopBarBackButtonClicked(val screen: Screen): CourseDetailsDisplayUIEvent()

    data object ClassTitleClicked: CourseDetailsDisplayUIEvent()
    data class ClassDeleteSwipe(val classDetails: ClassDetails): CourseDetailsDisplayUIEvent()

    data object TermTestTitleClicked : CourseDetailsDisplayUIEvent()
    data class EventDeleteSwipe(val event: Event): CourseDetailsDisplayUIEvent()

    data object AssignmentTitleClicked: CourseDetailsDisplayUIEvent()
}
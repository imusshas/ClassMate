package com.nasiat_muhib.classmate.domain.event

sealed class CourseDetailsDisplayUIEvent {
    data object CourseDetailsDisplayTopBarBackButtonClicked: CourseDetailsDisplayUIEvent()
    data object ClassTitleClicked: CourseDetailsDisplayUIEvent()
}
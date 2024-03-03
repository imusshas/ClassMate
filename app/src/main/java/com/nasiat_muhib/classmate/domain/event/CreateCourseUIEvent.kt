package com.nasiat_muhib.classmate.domain.event

sealed class CreateCourseUIEvent {

    data class CourseCodeChanged(val courseCode: String): CreateCourseUIEvent()
    data class CourseTitleChanged(val courseTitle: String): CreateCourseUIEvent()
    data class CourseCreditChanged(val courseCredit: String): CreateCourseUIEvent()
    data class CourseTeacherEmailChanged(val courseTeacherEmail: String): CreateCourseUIEvent()

    data object BackButtonClick: CreateCourseUIEvent()
    data object CreateClassButtonClick: CreateCourseUIEvent()
}
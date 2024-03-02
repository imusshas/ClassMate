package com.nasiat_muhib.classmate.domain.state

data class CreateCourseUIState (
    val courseCode: String = "",
    val courseTitle: String = "",
    val courseCredit: String = "",
    val courseTeacherEmail: String = "",

    val courseCodeError: String = "",
    val courseTitleError: String = "",
    val courseCreditError: String = "",
    val courseTeacherEmailError: String = "",
)
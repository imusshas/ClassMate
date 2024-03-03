package com.nasiat_muhib.classmate.domain.state

data class CreateCourseUIState (
    val courseCode: String = "",
    val courseTitle: String = "",
    val courseCredit: Float = 0f,
    val courseTeacherEmail: String = "",

    val courseCodeError: String? = null,
    val courseTitleError: String? = null,
    val courseCreditError: String? = null,
    val courseTeacherEmailError: String? = null,
    val createClassError: String? = null
)
package com.nasiat_muhib.classmate.domain.state

import com.nasiat_muhib.classmate.core.Constants.SEMESTERS

data class CreateCourseUIState (
    val courseCode: String = "",
    val courseTitle: String = "",
    val courseCredit: Double = 0.0,
    val courseSemester: String = SEMESTERS[0],
    val courseTeacherEmail: String = "",

    val courseCodeError: String? = null,
    val courseTitleError: String? = null,
    val courseCreditError: String? = null,
    val courseTeacherEmailError: String? = null,
    val createClassError: String? = null
)
package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.core.Constants.SEMESTERS
import com.nasiat_muhib.classmate.strings.COURSE_CODE
import com.nasiat_muhib.classmate.strings.COURSE_CREATOR
import com.nasiat_muhib.classmate.strings.COURSE_CREDIT
import com.nasiat_muhib.classmate.strings.COURSE_DEPARTMENT
import com.nasiat_muhib.classmate.strings.COURSE_SEMESTER
import com.nasiat_muhib.classmate.strings.COURSE_TEACHER
import com.nasiat_muhib.classmate.strings.COURSE_TITLE

data class Course(
    val courseCreator: String = "",
    val courseDepartment: String = "",
    val courseSemester: String = SEMESTERS[0],
    val courseCode: String = "",
    val courseTitle: String = "",
    val courseCredit: Float = 0f,
    val courseTeacher: String = ""
) {
    fun toMap(): Map<String, Any> = mapOf(
        COURSE_CREATOR to courseCreator,
        COURSE_DEPARTMENT to courseDepartment,
        COURSE_SEMESTER to courseSemester,
        COURSE_CODE to courseCode,
        COURSE_TITLE to courseTitle,
        COURSE_CREDIT to courseCredit,
        COURSE_TEACHER to courseTeacher
    )
}

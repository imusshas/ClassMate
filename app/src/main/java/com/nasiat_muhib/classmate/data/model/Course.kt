package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.core.Constants.SEMESTERS
import com.nasiat_muhib.classmate.strings.COURSE_CLASSES
import com.nasiat_muhib.classmate.strings.COURSE_CODE
import com.nasiat_muhib.classmate.strings.COURSE_CREATOR
import com.nasiat_muhib.classmate.strings.COURSE_CREDIT
import com.nasiat_muhib.classmate.strings.COURSE_DEPARTMENT
import com.nasiat_muhib.classmate.strings.COURSE_SEMESTER
import com.nasiat_muhib.classmate.strings.COURSE_TEACHER
import com.nasiat_muhib.classmate.strings.COURSE_TITLE
import com.nasiat_muhib.classmate.strings.ENROLLED_STUDENTS

data class Course(
    val courseCreator: String = "",
    val courseDepartment: String = "",
    val courseSemester: String = SEMESTERS[0],
    val courseCode: String = "",
    val courseTitle: String = "",
    val courseCredit: Double = 0.0,
    val courseTeacher: String = "",
    val courseClasses: List<String> = emptyList(),
    val enrolledStudents: List<String> = emptyList(),
) {
    fun toMap(): Map<String, Any> = mapOf(
        COURSE_CREATOR to courseCreator,
        COURSE_DEPARTMENT to courseDepartment,
        COURSE_SEMESTER to courseSemester,
        COURSE_CODE to courseCode,
        COURSE_TITLE to courseTitle,
        COURSE_CREDIT to courseCredit,
        COURSE_TEACHER to courseTeacher,
        COURSE_CLASSES to courseClasses,
        ENROLLED_STUDENTS to enrolledStudents
    )
}

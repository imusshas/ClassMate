package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.core.Constants.CODE
import com.nasiat_muhib.classmate.core.Constants.CREATOR
import com.nasiat_muhib.classmate.core.Constants.CREDIT
import com.nasiat_muhib.classmate.core.Constants.ENROLLED_STUDENTS
import com.nasiat_muhib.classmate.core.Constants.TEACHER
import com.nasiat_muhib.classmate.core.Constants.TITLE

data class Course(
    var code: String = "",
    var title: String = "",
    var credit: String = "",
    var creator: String = "",
    var teacher: String = "",
    var enrolledStudents: List<String> = emptyList(),
) {
    fun toMap(): Map<String, Any> = mapOf(
        CODE to code,
        TITLE to title,
        CREDIT to credit,
        CREATOR to creator,
        TEACHER to teacher,
        ENROLLED_STUDENTS to enrolledStudents
    )
}

package com.nasiat_muhib.classmate.data.model

import android.provider.ContactsContract.CommonDataKinds.Email
import com.nasiat_muhib.classmate.core.Constants.CODE
import com.nasiat_muhib.classmate.core.Constants.CREATOR
import com.nasiat_muhib.classmate.core.Constants.ENROLLED_STUDENTS
import com.nasiat_muhib.classmate.core.Constants.PASSWORD
import com.nasiat_muhib.classmate.core.Constants.TEACHER
import com.nasiat_muhib.classmate.core.Constants.TITLE

data class Course(
    var code: String = "",
    var title: String = "",
    var creator: String = "",
    var teacher: String = "",
    var password: String = "",
    var enrolledStudents: List<String> = emptyList(),
) {
    fun toMap(): Map<String, Any> = mapOf(
        CODE to code,
        TITLE to title,
        CREATOR to creator,
        TEACHER to teacher,
        PASSWORD to password,
        ENROLLED_STUDENTS to enrolledStudents
    )
}

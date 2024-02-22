package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.core.Constants.CLASS_DETAILS
import com.nasiat_muhib.classmate.core.Constants.CODE
import com.nasiat_muhib.classmate.core.Constants.PASSWORD
import com.nasiat_muhib.classmate.core.Constants.TEACHER
import com.nasiat_muhib.classmate.core.Constants.TITLE

data class Course(
    var code: String = "",
    var title: String = "",
    var creator: String = "",
    var teacher: String = "",
    var password: String = "",
    var classDetails: List<ClassDetails> = emptyList(),
) {
    fun toMap(): Map<String, Any> = mapOf(
        CODE to code,
        TITLE to title,
        TEACHER to teacher,
        PASSWORD to password,
        CLASS_DETAILS to classDetails
    )
}

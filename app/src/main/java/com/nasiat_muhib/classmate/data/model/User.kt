package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.strings.CREATED_COURSES
import com.nasiat_muhib.classmate.strings.DEPARTMENT
import com.nasiat_muhib.classmate.strings.EMAIL
import com.nasiat_muhib.classmate.strings.ENROLLED_COURSES
import com.nasiat_muhib.classmate.strings.FIRST_NAME
import com.nasiat_muhib.classmate.strings.LAST_NAME
import com.nasiat_muhib.classmate.strings.PASSWORD
import com.nasiat_muhib.classmate.strings.PHONE_NO
import com.nasiat_muhib.classmate.strings.REQUESTED_COURSES
import com.nasiat_muhib.classmate.strings.ROLE
import com.nasiat_muhib.classmate.strings.SESSION

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val role: String = "",
    val department: String = "",
    val session: String = "",
    val phoneNo: String = "",
    val email: String = "",
    val password: String = "",
    val createdCourses: List<String> = emptyList(),
    val enrolledCourses: List<String> = emptyList(),
    val requestedCourses: List<String> = emptyList()
) {
    fun toMap(): Map<String, Any> =  mapOf(
        FIRST_NAME to firstName,
        LAST_NAME to firstName,
        ROLE to role,
        DEPARTMENT to department,
        SESSION to session,
        PHONE_NO to phoneNo,
        EMAIL to email,
        PASSWORD to password,
        REQUESTED_COURSES to createdCourses,
        CREATED_COURSES to enrolledCourses,
        ENROLLED_COURSES to requestedCourses,
    )
}

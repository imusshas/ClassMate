package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.core.Constants.AVATAR_URL
import com.nasiat_muhib.classmate.core.Constants.BLOOD_GROUP
import com.nasiat_muhib.classmate.core.Constants.CREATED_COURSE
import com.nasiat_muhib.classmate.core.Constants.DEPARTMENT
import com.nasiat_muhib.classmate.core.Constants.EMAIL
import com.nasiat_muhib.classmate.core.Constants.ENROLLED_COURSE
import com.nasiat_muhib.classmate.core.Constants.FIRST_NAME
import com.nasiat_muhib.classmate.core.Constants.LAST_NAME
import com.nasiat_muhib.classmate.core.Constants.PASSWORD
import com.nasiat_muhib.classmate.core.Constants.PHONE_NO
import com.nasiat_muhib.classmate.core.Constants.REQUESTED_COURSE
import com.nasiat_muhib.classmate.core.Constants.ROLE
import com.nasiat_muhib.classmate.core.Constants.SESSION

data class User(
    var firstName: String = "",
    var lastName: String = "",
    var role: String = "",
    var department: String = "",
    var session: String = "",
    var bloodGroup: String = "",
    var phoneNo: String = "",
    var email: String = "",
    var password: String = "",
    var avatarUrl: String = "",
    var createdCourse: List<String> = emptyList(),
    var enrolledCourse: List<String> = emptyList(),
    var requestedCourse: List<String> = emptyList()
) {
    fun toMap(): Map<String, Any> = mapOf(
        FIRST_NAME to firstName,
        LAST_NAME to lastName,
        ROLE to role,
        DEPARTMENT to department,
        SESSION to session,
        BLOOD_GROUP to bloodGroup,
        PHONE_NO to phoneNo,
        EMAIL to email,
        PASSWORD to password,
        AVATAR_URL to avatarUrl,
        CREATED_COURSE to createdCourse,
        ENROLLED_COURSE to enrolledCourse,
        REQUESTED_COURSE to requestedCourse
    )
}

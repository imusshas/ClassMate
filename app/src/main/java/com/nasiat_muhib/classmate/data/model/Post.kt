package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.strings.POST_COURSE_CODE
import com.nasiat_muhib.classmate.strings.POST_CREATOR
import com.nasiat_muhib.classmate.strings.POST_CREATOR_FIRST_NAME
import com.nasiat_muhib.classmate.strings.POST_CREATOR_LAST_NAME
import com.nasiat_muhib.classmate.strings.POST_DESCRIPTION
import com.nasiat_muhib.classmate.strings.POST_TIMESTAMP
import java.sql.Timestamp

data class Post(
    val creator: String = "",
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis()),
    val firstName: String = "",
    val lastName: String = "",
    val courseCode: String = "",
    val description: String = ""
) {
    fun toMap(): Map<String, Any> = mapOf(
        POST_CREATOR to creator,
        POST_TIMESTAMP to timestamp,
        POST_CREATOR_FIRST_NAME to firstName,
        POST_CREATOR_LAST_NAME to lastName,
        POST_COURSE_CODE to courseCode,
        POST_DESCRIPTION to description
    )
}

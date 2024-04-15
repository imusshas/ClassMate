package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.strings.CREATION_TIME
import com.nasiat_muhib.classmate.strings.NOTIFICATION_COURSE_DEPARTMENT
import com.nasiat_muhib.classmate.strings.NOTIFICATION_COURSE_TITLE
import com.nasiat_muhib.classmate.strings.NOTIFICATION_TYPE
import com.nasiat_muhib.classmate.strings.NOTIFICATION__COURSE_CODE
import com.nasiat_muhib.classmate.strings.READ_STATUS

data class Notification(
    val creationTime: Long,
    val type: String,
    val courseDepartment: String,
    val courseCode: String,
    val courseTitle: String,
    val isRead: Boolean
) {
    fun toMap() : Map<String, Any> = mapOf(
        CREATION_TIME to  creationTime,
        NOTIFICATION_TYPE to type,
        NOTIFICATION_COURSE_DEPARTMENT to courseDepartment,
        NOTIFICATION__COURSE_CODE to courseCode,
        NOTIFICATION_COURSE_TITLE to courseTitle,
        READ_STATUS to isRead
    )
}

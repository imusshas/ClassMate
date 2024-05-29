package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.core.Constants.EVENTS
import com.nasiat_muhib.classmate.strings.EVENT_CLASSROOM
import com.nasiat_muhib.classmate.strings.EVENT_COURSE_CODE
import com.nasiat_muhib.classmate.strings.EVENT_COURSE_CREATOR
import com.nasiat_muhib.classmate.strings.EVENT_DAY
import com.nasiat_muhib.classmate.strings.EVENT_DEPARTMENT
import com.nasiat_muhib.classmate.strings.EVENT_HOUR
import com.nasiat_muhib.classmate.strings.EVENT_MINUTE
import com.nasiat_muhib.classmate.strings.EVENT_MONTH
import com.nasiat_muhib.classmate.strings.EVENT_NO
import com.nasiat_muhib.classmate.strings.EVENT_SHIFT
import com.nasiat_muhib.classmate.strings.EVENT_TYPE
import com.nasiat_muhib.classmate.strings.EVENT_YEAR

data class Event(
    val type: String = EVENTS[0],
    val courseCode: String = "",
    val courseCreator: String = "",
    val department: String = "",
    val eventNo: Int = -1,
    val classroom: String = "",
    val day: Int = -1,
    val month: String = "",
    val year: Int = -1,
    val hour: Int = -1,
    val minute: Int = -1,
    val shift: String = ""
) {
    fun toMap(): Map<String, Any> = mapOf(
        EVENT_TYPE to type,
        EVENT_NO to eventNo,
        EVENT_COURSE_CODE to courseCode,
        EVENT_COURSE_CREATOR to courseCreator,
        EVENT_DEPARTMENT to  department,
        EVENT_CLASSROOM to classroom,
        EVENT_DAY to day,
        EVENT_MONTH to month,
        EVENT_YEAR to year,
        EVENT_HOUR to hour,
        EVENT_MINUTE to minute,
        EVENT_SHIFT to shift,
    )

}

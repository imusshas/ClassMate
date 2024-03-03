package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.core.Constants.WEEK_DAYS
import com.nasiat_muhib.classmate.strings.CLASSROOM
import com.nasiat_muhib.classmate.strings.END_HOUR
import com.nasiat_muhib.classmate.strings.END_MINUTE
import com.nasiat_muhib.classmate.strings.END_SHIFT
import com.nasiat_muhib.classmate.strings.SECTION
import com.nasiat_muhib.classmate.strings.START_HOUR
import com.nasiat_muhib.classmate.strings.START_MINUTE
import com.nasiat_muhib.classmate.strings.START_SHIFT
import com.nasiat_muhib.classmate.strings.WEEKDAY

data class ClassDetails(
    val weekDay: String = WEEK_DAYS[0],
    val classroom: String = "",
    val section: String = "",
    val startHour: Int = -1,
    val startMinute: Int = -1,
    val startShift: String = "",
    val endHour: Int = -1,
    val endMinute: Int = -1,
    val endShift: String = "",
) {
    fun toMap(): Map<String, Any> = mapOf(
        WEEKDAY to weekDay,
        CLASSROOM to classroom,
        SECTION to section,
        START_HOUR to startHour,
        START_MINUTE to startMinute,
        START_SHIFT to startShift,
        END_HOUR to endHour,
        END_MINUTE to endMinute,
        END_SHIFT to endShift,
    )
}

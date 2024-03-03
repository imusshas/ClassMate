package com.nasiat_muhib.classmate.domain.state

import com.nasiat_muhib.classmate.core.Constants.WEEK_DAYS
import com.nasiat_muhib.classmate.strings.WEEKDAY

data class CreateClassUIState(
    val weekDay: String = WEEK_DAYS[0],
    val classroom: String = "",
    val section: String = "",
    val startHour: Int = 0,
    val startMinute: Int = 0,
    val startShift: String = "",
    val endHour: Int = 0,
    val endMinute: Int = 0,
    val endShift: String = "",

    val classroomError: String? = null,
    val sectionError: String? = null,
    val timeError: String? = null
)

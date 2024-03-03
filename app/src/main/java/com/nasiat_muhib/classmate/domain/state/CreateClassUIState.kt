package com.nasiat_muhib.classmate.domain.state

data class CreateClassUIState(
    val weekDay: String = "",
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

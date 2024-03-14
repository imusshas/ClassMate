package com.nasiat_muhib.classmate.domain.state

import com.nasiat_muhib.classmate.core.Constants.EVENTS

data class EventUIState(
    val type: String = EVENTS[0],
    val classroom: String = "",
    val day: Int = -1,
    val month: String = "",
    val year: Int = -1,
    val hour: Int = -1,
    val minute: Int = -1,
    val shift: String = ""
)

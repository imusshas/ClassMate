package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.core.Constants.ACTIVE_STATUS
import com.nasiat_muhib.classmate.core.Constants.PLACE
import com.nasiat_muhib.classmate.core.Constants.TIME
import com.nasiat_muhib.classmate.core.Constants.WEEK_DAY

data class ClassDetails (
    var weekDay: String = "",
    var time: String = "",
    var place: String = "",
    var isActive: Boolean = true
) {
    fun toMap(): Map<String, Any> = mapOf(
        WEEK_DAY to weekDay,
        TIME to time,
        PLACE to place,
        ACTIVE_STATUS to isActive
    )
}

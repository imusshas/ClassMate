package com.nasiat_muhib.classmate.data.model

import com.nasiat_muhib.classmate.core.Constants.ACTIVE_STATUS
import com.nasiat_muhib.classmate.core.Constants.HOUR
import com.nasiat_muhib.classmate.core.Constants.MINUTE
import com.nasiat_muhib.classmate.core.Constants.CLASSROOM
import com.nasiat_muhib.classmate.core.Constants.SHIFT
import com.nasiat_muhib.classmate.core.Constants.WEEK_DAY

data class ClassDetails (
    var weekDay: String = "",
    var hour: Int = 0,
    var minute: Int = 0,
    var shift: String = "",
    var classroom: String = "",
    var isActive: Boolean = true
) {
    fun toMap(): Map<String, Any> = mapOf(
        WEEK_DAY to weekDay,
        HOUR to hour,
        MINUTE to minute,
        SHIFT to shift,
        CLASSROOM to classroom,
        ACTIVE_STATUS to isActive
    )
}

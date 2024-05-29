package com.nasiat_muhib.classmate.data.model

import android.util.Log
import com.nasiat_muhib.classmate.core.Constants.WEEK_DAYS
import com.nasiat_muhib.classmate.strings.ACTIVE_STATUS
import com.nasiat_muhib.classmate.strings.CLASSROOM
import com.nasiat_muhib.classmate.strings.CLASS_COURSE_CODE
import com.nasiat_muhib.classmate.strings.CLASS_COURSE_CREATOR
import com.nasiat_muhib.classmate.strings.CLASS_DEPARTMENT
import com.nasiat_muhib.classmate.strings.CLASS_NO
import com.nasiat_muhib.classmate.strings.END_HOUR
import com.nasiat_muhib.classmate.strings.END_MINUTE
import com.nasiat_muhib.classmate.strings.END_SHIFT
import com.nasiat_muhib.classmate.strings.SECTION
import com.nasiat_muhib.classmate.strings.START_HOUR
import com.nasiat_muhib.classmate.strings.START_MINUTE
import com.nasiat_muhib.classmate.strings.START_SHIFT
import com.nasiat_muhib.classmate.strings.WEEKDAY

data class ClassDetails(
    val classDepartment: String = "",
    val classCourseCode: String = "",
    val classCourseCreator: String = "",
    val classNo: Int = -1,
    val weekDay: String = WEEK_DAYS[0],
    val classroom: String = "",
    val section: String = "",
    val startHour: Int = -1,
    val startMinute: Int = -1,
    val startShift: String = "",
    val endHour: Int = -1,
    val endMinute: Int = -1,
    val endShift: String = "",
    val isActive: Boolean = true
) {
    fun toMap(): Map<String, Any> = mapOf(
        CLASS_DEPARTMENT to classDepartment,
        CLASS_COURSE_CODE to classCourseCode,
        CLASS_COURSE_CREATOR to classCourseCreator,
        CLASS_NO to classNo,
        WEEKDAY to weekDay,
        CLASSROOM to classroom,
        SECTION to section,
        START_HOUR to startHour,
        START_MINUTE to startMinute,
        START_SHIFT to startShift,
        END_HOUR to endHour,
        END_MINUTE to endMinute,
        END_SHIFT to endShift,
        ACTIVE_STATUS to isActive
    )

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "${classroom.first()}${classroom.last()}",
            classroom,
        )

        return matchingCombinations.any { it.contains(query, ignoreCase = true) || query.contains(it, ignoreCase = true) }
    }
    companion object {
        const val TAG = "ClassDetails"
    }
}

package com.nasiat_muhib.classmate.core

import com.nasiat_muhib.classmate.core.Constants.AM
import com.nasiat_muhib.classmate.core.Constants.PM

object UtilityVariables {
    val DAYS_OF_WEEK = listOf(
        Constants.SUNDAY,
        Constants.MONDAY,
        Constants.TUESDAY,
        Constants.WEDNESDAY,
        Constants.THURSDAY,
        Constants.FRIDAY,
        Constants.SATURDAY
    )
    val HOURS_OF_DAY = (1..12).toList()
    val MINUTES_OF_HOUR = (0..59).toList()

    val AM_PM = listOf<String>(AM, PM)
}
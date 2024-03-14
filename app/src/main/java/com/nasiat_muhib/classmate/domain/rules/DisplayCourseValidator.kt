package com.nasiat_muhib.classmate.domain.rules

import android.util.Log

object DisplayCourseValidator {

    /***********************    Class    ***************************/
    fun validateSection(section: String): DisplayCourseValidationResult {
        var message: String? = null
        if (section.isBlank()) message = "Section can't be empty"

        return DisplayCourseValidationResult(message)
    }

    fun validateHour(hour: String): DisplayCourseValidationResult {
        var message: String? = null
        if (hour.isBlank()) message = "Hour can't be empty"
        else if (hour.toInt() !in 1..12) {
            message = "Invalid Hour"
        }

        return DisplayCourseValidationResult(message)
    }

    fun validateMinute(minute: String): DisplayCourseValidationResult {
        var message: String? = null
        if (minute.isBlank()) message = "Minute can't be empty"
        else if (minute.toInt() !in 0..59) {
            message = "Invalid Minute"
        }
        return DisplayCourseValidationResult(message)
    }

    /***********************    Class    ***************************/



    data class DisplayCourseValidationResult(
        val message: String? = null
    )

}
package com.nasiat_muhib.classmate.domain.rules

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.LocalDate

object DisplayCourseValidator {

    /***********************    Classroom    ***************************/
    fun validateClassroom(classroom: String): DisplayCourseValidationResult {
        var message: String? = null
        if (classroom.isBlank()) message = "Section can't be empty"

        return DisplayCourseValidationResult(message)
    }
    /***********************    Classroom    ***************************/

    /***********************    Time    ***************************/
    fun validateHour(hour: String): DisplayCourseValidationResult {
        var message: String? = null
        if (hour.isBlank()) message = "Hour can't be empty"
        else if (hour.toInt() !in 1..12) {
            message = "Invalid Hour: $hour"
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

    /***********************    Time    ***************************/


    /***********************    Date    ***************************/
    fun validateDate(day: String, month: String, year: String): DisplayCourseValidationResult {
        var message: String? = null
        val months = listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")
        val has30Days = listOf("APR", "JUN", "SEP", "NOV")

        if (day.isBlank() || month.isBlank() || year.isBlank()) {
            message = "Date can't be empty"
        }  else if (month.length != 3) {
            message = "Month must be of 3 capital letters"
        } else if (month !in "A".."Z") {
            message = "Month must contain capital letters only"
        } else if (day.toInt() !in  1..31 || month !in months || year.toInt() < LocalDate.now().year) {
            message = "Invalid date"
        } else if (month !in has30Days && day == "31") {
            message = "$month can't have 31 days"
        } else if (month == "FEB" && day.toInt() > 29) {
            message = "FEB can't have $day days"
        } else if (!isLeapYear(year.toInt()) && month == "FEB" && day == "29") {
            message = "$year is not a leap year"
        }

        return DisplayCourseValidationResult(message)
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
    /***********************    Date    ***************************/

    /***********************    Resource Link    ***************************/
    fun validateTitle(title: String): DisplayCourseValidationResult {
        var message: String? = null

        if (title.isBlank()) message = "Title can't be empty"

        return DisplayCourseValidationResult(message)
    }

    fun validateLink(link: String): DisplayCourseValidationResult {
        var message: String? = null

        if (link.isBlank()) message = "Link can't be empty"
        else {
            try {

                val client = OkHttpClient()
                val request = Request.Builder().url(link).head().build()
                val  response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    message = "Invalid Link"
                }
            } catch (e: Exception) {
                Log.d(TAG, "validateLink: ${e.localizedMessage}")
                if (e.localizedMessage != null)
                message = "Invalid Link. Link must contain http:// or https://"
            }
        }

        return DisplayCourseValidationResult(message)
    }
    /***********************    Resource Link    ***************************/


    data class DisplayCourseValidationResult(
        val message: String? = null
    )

    const val TAG = "DisplayCourseValidator"

}
package com.nasiat_muhib.classmate.domain.rules

import android.util.Log
import android.util.Patterns
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.strings.AM
import com.nasiat_muhib.classmate.strings.PM

object CreateCourseValidator {
    fun validateCourseCode(courseCode: String): CreateCourseValidationResult {
        var message: String? = null
        val code = getIntCourseCode(courseCode)
//        Log.d(TAG, "validateCourseCode: $code")
        if (courseCode.isBlank()) message = "Course code can't be empty"
        if(code == null) message = "Invalid course code"
        return CreateCourseValidationResult(message)
    }

    fun validateCourseTitle(courseTitle: String): CreateCourseValidationResult {
        var message: String? = null
        if (courseTitle.isBlank()) message = "Course Title can't be empty"

        return CreateCourseValidationResult(message)
    }

    fun validateCourseCredit(courseCredit: String): CreateCourseValidationResult {
        var message: String? = null

        if (courseCredit.isBlank()) message = "Course Credit can't be zero or empty"
        else {
            val cCredit = courseCredit.toFloat()
            val cCreditInt = cCredit.toInt()
            if (cCredit - cCreditInt != 0f && cCredit - cCreditInt != 0.5f) {
//                Log.d(TAG, "validateCourseCredit: $cCredit - $cCreditInt = ${cCredit - cCreditInt}")
                message = "Invalid course credit"
            }
        }

        return CreateCourseValidationResult(message)
    }

    fun validateCourseTeacherEmail(courseTeacherEmail: String): CreateCourseValidationResult {
        var message: String? = null
        if (courseTeacherEmail.isBlank()) message = "Please Select A Teacher"

        return CreateCourseValidationResult(message)
    }

    fun validateClassDetails(classDetailsList: List<ClassDetails>): CreateCourseValidationResult {
        var message: String? = null
        if (classDetailsList.isEmpty()) message = "There should be at least one class"

        return CreateCourseValidationResult(message)
    }

    fun validateClassroom(classroom: String): CreateCourseValidationResult {
        var message: String? = null
        if (classroom.isBlank()) message = "Classroom can't be empty"

        return CreateCourseValidationResult(message)
    }

    fun validateSection(section: String): CreateCourseValidationResult {
        var message: String? = null
        if (section.isBlank()) message = "Section can't be empty"

        return CreateCourseValidationResult(message)
    }

    fun validateHour(hour: String): CreateCourseValidationResult {
        var message: String? = null
        if (hour.isBlank()) message = "Hour can't be empty"
        else if (hour.toInt() !in 1..12) {
            message = "Invalid Hour"
            Log.d(TAG, "validateHour: ${hour.toInt()}")
        }

        return CreateCourseValidationResult(message)
    }

    fun validateMinute(minute: String): CreateCourseValidationResult {
        var message: String? = null
        if (minute.isBlank()) message = "Minute can't be empty"
        else if (minute.toInt() !in 0..59) {
            message = "Invalid Minute"
            Log.d(TAG, "validateMinute: ${minute.toInt()}")
        }
        return CreateCourseValidationResult(message)
    }


    fun validateDuration(
        courseCode: String,
        courseCredit: Float,
        startHour: Int,
        startMinute: Int,
        startShift: String,
        endHour: Int,
        endMinute: Int,
        endShift: String,
    ): CreateCourseValidationResult {
        var message: String? = null
        val intCode = getIntCourseCode(courseCode)
        if (startHour == endHour && startMinute == endMinute && startShift == endShift) message = "Start time & end time can't be same"
        else if(intCode != null) {
            val isLab = intCode % 2 == 0

        }

//        Log.d(TAG, "validateDuration: hour = $hour, durationHour = $durationHour")
//        Log.d(TAG, "validateDuration: minute = $minute, durationHour = $durationMinute")
        return CreateCourseValidationResult(message)
    }


    private fun getIntCourseCode(courseCode: String): Int? {
        var code = ""
        courseCode.forEach {
            if (it.code in 48..57) {
                code += it
            }
//            Log.d(TAG, "getIntCourseCode: $code for  $it")
        }

        return if(code == "") null else code.toInt()
    }

    data class CreateCourseValidationResult(
        val message: String? = null
    )

    private const val TAG = "CreateCourseValidator"
}


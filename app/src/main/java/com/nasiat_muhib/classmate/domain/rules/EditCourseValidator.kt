package com.nasiat_muhib.classmate.domain.rules

import android.util.Log
import com.nasiat_muhib.classmate.data.model.ClassDetails

object EditCourseValidator {

    fun validateEditedCourseCode(prevCode: String, courseCode: String): EditCourseValidationResult {
        var message: String? = null
        val code = getIntCourseCode(courseCode)

        if (prevCode == courseCode) message = "Course code not edited"
        else if (courseCode.isBlank()) message = "Course code can't be empty"
        else if (code == null) message = "Invalid course code"

        return EditCourseValidationResult(message)
    }

    fun validateEditedCourseTitle(
        prevTitle: String,
        courseTitle: String,
    ): EditCourseValidationResult {
        var message: String? = null
        if (courseTitle.isBlank()) message = "Course title can't be empty"
        else if (prevTitle == courseTitle) message = "Course title not edited"

        return EditCourseValidationResult(message)
    }

    fun validateEditedCourseCredit(
        prevCredit: String,
        courseCredit: String,
    ): EditCourseValidationResult {
        var message: String? = null

        if (courseCredit.isBlank()) message = "Course credit can't be zero or empty"
        else {
            val cCredit = courseCredit.toDouble()
            val cCreditInt = cCredit.toInt()
            if (cCredit - cCreditInt != 0.0 && cCredit - cCreditInt != 0.5) {
//                Log.d(TAG, "validateCourseCredit: $cCredit - $cCreditInt = ${cCredit - cCreditInt}")
                message = "Invalid course credit"
            } else if (prevCredit == courseCredit) message = "Course credit not edited"
        }

        return EditCourseValidationResult(message)
    }

    fun validateEditedCourseTeacherEmail(
        prevTeacher: String,
        courseTeacherEmail: String,
    ): EditCourseValidationResult {
        var message: String? = null
        if (courseTeacherEmail.isBlank()) message = "Please Select A Teacher"
        else if (prevTeacher == courseTeacherEmail) message = "Course Teacher Not edited"

        return EditCourseValidationResult(message)
    }

    fun validateEditedClassDetails(
        prevClassDetailsList: List<ClassDetails>,
        classDetailsList: List<ClassDetails>,
    ): EditCourseValidationResult {
        var message: String? = null
        if (classDetailsList.isEmpty()) message = "There should be at least one class"
        else if (prevClassDetailsList == classDetailsList) message = "Class details Not edited"

        return EditCourseValidationResult(message)
    }

    fun validateEditedClassroom(
        prevClassroom: String,
        classroom: String,
    ): EditCourseValidationResult {
        var message: String? = null
        if (classroom.isBlank()) message = "Classroom can't be empty"
        else if (prevClassroom == classroom) message = "Classroom not edited"

        return EditCourseValidationResult(message)
    }

    fun validateEditedSection(prevSection: String, section: String): EditCourseValidationResult {
        var message: String? = null
        if (section.isBlank()) message = "Section can't be empty"
        else if (prevSection == section) message = "Section not edited"

        return EditCourseValidationResult(message)
    }

    fun validateEditedHour(prevHour: String, hour: String): EditCourseValidationResult {
        var message: String? = null
        if (hour.isBlank()) message = "Hour can't be empty"
        else if (hour.toInt() !in 1..12) {
            message = "Invalid Hour"
            Log.d(TAG, "validateHour: ${hour.toInt()}")
        } else if (prevHour == hour) message = "Hour not edited"

        return EditCourseValidationResult(message)
    }

    fun validateEditedMinute(prevMinute: String, minute: String): EditCourseValidationResult {
        var message: String? = null
        if (minute.isBlank()) message = "Minute can't be empty"
        else if (minute.toInt() !in 0..59) {
            message = "Invalid Minute"
            Log.d(TAG, "validateMinute: ${minute.toInt()}")
        } else if (prevMinute == minute) message = "Minute not edited"

        return EditCourseValidationResult(message)
    }


    fun validateEditedDuration(
        courseCode: String, courseCredit: Double,
        prevStartHour: Int, startHour: Int,
        prevStartMinute: Int, startMinute: Int,
        prevStartShift: String, startShift: String,
        prevEndHour: Int, endHour: Int,
        prevEndMinute: Int, endMinute: Int,
        prevEndShift: String, endShift: String,
    ): EditCourseValidationResult {
        var message: String? = null
        val intCode = getIntCourseCode(courseCode)
        if (startHour == endHour && startMinute == endMinute && startShift == endShift) message =
            "Start time & end time can't be same"
        else if (
            prevStartHour == startHour && prevStartMinute == startMinute && prevStartShift == startShift &&
            prevEndHour == endHour && prevEndMinute == endMinute && prevEndShift == endShift
                ) message = "Time not edited"

//        Log.d(TAG, "validateDuration: hour = $hour, durationHour = $durationHour")
//        Log.d(TAG, "validateDuration: minute = $minute, durationHour = $durationMinute")
            return EditCourseValidationResult(message)
    }


    private fun getIntCourseCode(courseCode: String): Int? {
        var code = ""
        courseCode.forEach {
            if (it.code in 48..57) {
                code += it
            }
//            Log.d(TAG, "getIntCourseCode: $code for  $it")
        }

        return if (code == "") null else code.toInt()
    }

    data class EditCourseValidationResult(
        val message: String? = null,
    )

    private const val TAG = "EditCourseValidator"
}
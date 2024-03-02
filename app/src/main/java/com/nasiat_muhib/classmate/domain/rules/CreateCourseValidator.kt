package com.nasiat_muhib.classmate.domain.rules

import android.util.Patterns

object CreateCourseValidator {
    fun validateCourseCode(courseCode: String): CreateCourseValidationResult {
        var message: String? = null
        if(courseCode.isBlank()) message = "Course code can't be empty"

        return CreateCourseValidationResult(message)
    }

    fun validateCourseTitle(courseTitle: String): CreateCourseValidationResult {
        var message: String? = null
        if(courseTitle.isBlank()) message = "Course Title can't be empty"

        return CreateCourseValidationResult(message)
    }

    fun validateCourseCredit(courseCredit: String): CreateCourseValidationResult {
        var message: String? = null
        if(courseCredit.isBlank()) message = "Course Credit can't be empty"

        return CreateCourseValidationResult(message)
    }

    fun validateCourseTeacherEmail(courseTeacherEmail: String): CreateCourseValidationResult {
        var message: String? = null
        if(courseTeacherEmail.isBlank()) message = "Email can't be empty"
        else if(!Patterns.EMAIL_ADDRESS.matcher(courseTeacherEmail).matches()) message = "Invalid Email"

        return CreateCourseValidationResult(message)
    }

    fun validateClassroom(classroom: String): CreateCourseValidationResult {
        var message: String? = null
        if(classroom.isBlank()) message = "Classroom can't be empty"

        return CreateCourseValidationResult(message)
    }

    fun validateHour(hour: String): CreateCourseValidationResult {
        var message: String? = null
        if(hour.isBlank()) message = "Hour can't be empty"

        return CreateCourseValidationResult(message)
    }

    fun validateMinute(minute: String): CreateCourseValidationResult {
        var message: String? = null
        if(minute.isBlank()) message = "Minute can't be empty"

        return CreateCourseValidationResult(message)
    }

    data class CreateCourseValidationResult (
        val message: String? = null
    )
}


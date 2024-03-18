package com.nasiat_muhib.classmate.domain.rules

import android.util.Log

object CreatePostValidator {

    fun validateCourseCode(courseCode: String, createdOrTeacher: List<String>): CreatePostValidationResult {
        var message: String? = null
        val intCode = getIntCourseCode(courseCode)
        var isValid = false
        if (courseCode.isBlank()) message = "Course code can't be empty"
        else if(intCode == null) message = "Invalid course code"
        else {
            createdOrTeacher.forEach {
                val code = it.substringAfter(":")
                Log.d(TAG, "validateCourseCode: code from list: $code : courseCode: $courseCode")
                if (code == courseCode) {
                    isValid = true
                }
            }

            if (!isValid) {
                message = "You neither created nor the teacher of the course"
            }
        }
        return CreatePostValidationResult(message)
    }

    fun validateDescription(description: String): CreatePostValidationResult {
        var message: String? = null
        if (description.isBlank()) message = "Description can't be empty"
        return CreatePostValidationResult(message)
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

    data class CreatePostValidationResult (
        val message: String? = null
    )

    private const val TAG = "CreatePostValidator"
}
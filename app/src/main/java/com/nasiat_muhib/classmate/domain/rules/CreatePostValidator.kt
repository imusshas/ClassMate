package com.nasiat_muhib.classmate.domain.rules

object CreatePostValidator {

    fun validateCourseCode(courseCode: String, createdOrTeacher: List<String>): CreatePostValidationResult {
        var message: String? = null
        val code = getIntCourseCode(courseCode)
        if (courseCode.isBlank()) message = "Course code can't be empty"
        else if(code == null) message = "Invalid course code"
//        else if (!createdOrTeacher.contains(courseCode)) message = "You neither created nor the teacher of the course"
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
}
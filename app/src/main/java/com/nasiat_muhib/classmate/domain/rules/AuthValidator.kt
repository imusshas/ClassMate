package com.nasiat_muhib.classmate.domain.rules

import android.util.Patterns

object AuthValidator {

    fun validateFirstName(firstName: String): AuthValidationResult {
        var message: String? = null
        if(firstName.isBlank()) message = "First name can't be empty"

        return AuthValidationResult(message)
    }

    fun validateLastName(lastName: String): AuthValidationResult {
        var message: String? = null
        if(lastName.isBlank()) message = "Last name can't be empty"

        return AuthValidationResult(message)
    }

    fun validateDepartment(department: String): CreateCourseValidator.CreateCourseValidationResult {
        var message: String? = null
        if (department.isBlank()) message = "Department can't be empty"
        else if (department.length != 3) message = "Department must be of three characters"
        else if (department !in "A".."Z") message = "Department should be of capital letters only"

        return CreateCourseValidator.CreateCourseValidationResult(message)
    }

    fun validateEmail(email: String): AuthValidationResult {
        var message: String? = null
        if(email.isBlank()) message = "Email can't be empty"
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) message = "Invalid Email"

        return AuthValidationResult(message)
    }

    fun validatePassword(password: String): AuthValidationResult {
        var message: String? = null
        val number = Regex("[0-9]")
        val smallChar = Regex("[a-z]")
        val capitalChar = Regex("[A-Z]")
        if(password.isBlank()) message = "Password can't be empty"
        else if(password.length < 8) message = "Password must contain at least 8 characters"
        else if(!number.containsMatchIn(password)) message = "Password must contain one number"
        else if(!smallChar.containsMatchIn(password) && !capitalChar.containsMatchIn(password)) message = "Password must contain one character"

        return AuthValidationResult(message)
    }

    data class AuthValidationResult(
        val message: String? = null,
    )
}

package com.nasiat_muhib.classmate.domain.rules

import android.util.Patterns

object AuthValidator {

    fun validateFirstName(firstName: String): ValidationResult {
        var message: String? = null
        if(firstName.isEmpty()) message = "First name can't be empty"

        return ValidationResult(message)
    }

    fun validateLastName(lastName: String): ValidationResult {
        var message: String? = null
        if(lastName.isEmpty()) message = "Last name can't be empty"

        return ValidationResult(message)
    }

    fun validateEmail(email: String): ValidationResult {
        var message: String? = null
        if(email.isBlank()) message = "Email can't be blank"
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) message = "Invalid Email"

        return ValidationResult(message)
    }

    fun validatePassword(password: String): ValidationResult {
        var message: String? = null
        val number = Regex("[0-9]")
        val smallChar = Regex("[a-z]")
        val capitalChar = Regex("[A-Z]")
        if(password.isBlank()) message = "Password can't be blank"
        else if(password.length < 8) message = "Password must contain at least 8 characters"
        else if(!number.containsMatchIn(password)) message = "Password must contain one number"
        else if(!smallChar.containsMatchIn(password) && !capitalChar.containsMatchIn(password)) message = "Password must contain one character"

        return ValidationResult(message)
    }
}

data class ValidationResult(
    val message: String? = null,
    )
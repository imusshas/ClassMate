package com.nasiat_muhib.classmate.domain.rules

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
        if(email.isEmpty()) message = "Email can't be empty"

        return ValidationResult(message)
    }

    fun validatePassword(password: String): ValidationResult {
        var message: String? = null
        val number = Regex("[0-9]")
        val smallChar = Regex("[a-z]")
        val capitalChar = Regex("[A-Z]")
        if(password.isEmpty()) message = "Password can't be empty"
        else if(password.length < 8) message = "Password must contain at least 8 characters"
        else if(!number.containsMatchIn(password)) message = "Password must contain one number"
        else if(!smallChar.containsMatchIn(password) && !capitalChar.containsMatchIn(password)) message = "Password must contain one character"

        return ValidationResult(message)
    }
}

data class ValidationResult(
    val message: String? = null,
    )
package com.nasiat_muhib.classmate.domain.rules

import android.provider.ContactsContract.CommonDataKinds.Email

object Validator {

    fun validateFirstName(firstName: String): ValidationResult {
        return ValidationResult(
            firstName.isNotEmpty() && firstName.length > 5
        )
    }

    fun validateLastName(lastName: String): ValidationResult {
        return ValidationResult(
            lastName.isNotEmpty() && lastName.length > 5
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            email.isNotEmpty()
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            password.isNotEmpty() && password.length > 5
        )
    }
}

data class ValidationResult(
    val status: Boolean = false,

)
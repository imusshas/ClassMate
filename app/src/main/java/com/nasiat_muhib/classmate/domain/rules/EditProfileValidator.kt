package com.nasiat_muhib.classmate.domain.rules

object EditProfileValidator {

    fun validateSession(session: String): EditProfileValidationResult {
        var message: String? = null
        val regex4 = Regex("^\\d{4}-\\d{4}$")
        val regex2 = Regex("^\\d{4}-\\d{2}$")
        if (session.isNotBlank() && !regex4.matches(session) && !regex2.matches(session)) {
            message = "Invalid Session"
        }

        return EditProfileValidationResult(message)
    }

    fun validateBloodGroup(bloodGroup: String): EditProfileValidationResult {
        val bloodGroups = setOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        var message: String? = null
        if (bloodGroup.isNotBlank() && !bloodGroups.contains(bloodGroup)) {
            message = "Invalid Blood Group"
        }

        return EditProfileValidationResult(message)
    }

    fun validatePhoneNo(phoneNo: String):EditProfileValidationResult {
        var message: String? = null
        val phoneRegex = "^[\\+]?[(]?[0-9]{3}[)]?[\\s.-]?[0-9]{3}[\\s.-]?[0-9]{4}$".toRegex()
        if (phoneNo.isNotBlank() && phoneRegex.matches(phoneNo)) {
            message = "Invalid Phone Number"
        }

        return EditProfileValidationResult(message)
    }


    data class EditProfileValidationResult(
        val message: String? = null,
    )
}
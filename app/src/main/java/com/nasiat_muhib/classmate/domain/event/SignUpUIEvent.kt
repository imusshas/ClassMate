package com.nasiat_muhib.classmate.domain.event

sealed class SignUpUIEvent {
    data class FirstNameChanged(val firstName: String): SignUpUIEvent()
    data class LastNameChanged(val lastName: String): SignUpUIEvent()

    data class RoleChanged(val role: String): SignUpUIEvent()

    data class PhoneNoChanged(val phoneNo: String): SignUpUIEvent()
    data class EmailChanged(val email: String): SignUpUIEvent()
    data class DepartmentChanged(val department: String): SignUpUIEvent()
    data class PasswordChanged(val password: String): SignUpUIEvent()

    data object SignUpButtonClicked: SignUpUIEvent()

    data class OTPChanged(val otp: String): SignUpUIEvent()
    data object VerifyOTPButtonClicked: SignUpUIEvent()
}
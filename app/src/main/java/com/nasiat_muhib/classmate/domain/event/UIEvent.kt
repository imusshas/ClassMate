package com.nasiat_muhib.classmate.domain.event

sealed class UIEvent {
    data class FirstNameChanged(val firstName: String): UIEvent()
    data class LastNameChanged(val lastName: String): UIEvent()
    data class EmailChanged(val email: String): UIEvent()
    data class PasswordChanged(val password: String): UIEvent()

    object SignUpButtonClicked: UIEvent()
}
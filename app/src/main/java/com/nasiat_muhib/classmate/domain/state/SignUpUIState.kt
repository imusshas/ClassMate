package com.nasiat_muhib.classmate.domain.state

data class SignUpUIState (
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",

    val firstNameError: Boolean = false,
    val lastNameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
)
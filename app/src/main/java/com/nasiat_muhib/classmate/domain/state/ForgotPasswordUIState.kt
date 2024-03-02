package com.nasiat_muhib.classmate.domain.state

data class ForgotPasswordUIState (
    val email: String = "",

    val emailError: String? = null
)
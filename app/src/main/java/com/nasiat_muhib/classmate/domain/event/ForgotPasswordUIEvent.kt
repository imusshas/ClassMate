package com.nasiat_muhib.classmate.domain.event

sealed class ForgotPasswordUIEvent {

    data class EmailChanged(val email: String): ForgotPasswordUIEvent()

    data object ForgotPasswordButtonClick: ForgotPasswordUIEvent()
}
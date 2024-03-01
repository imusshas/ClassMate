package com.nasiat_muhib.classmate.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object SignInScreen: Screen()
    object SignUpScreen: Screen()
    object ForgotPasswordScreen: Screen()
}

object ClassMateAppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignInScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}
package com.nasiat_muhib.classmate.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

sealed class Screen {
    object SignInScreen: Screen()
    object SignUpScreen: Screen()
    object ForgotPasswordScreen: Screen()

    object HomeScreen: Screen()
}

object ClassMateAppRouter {
    private val currentUser = Firebase.auth.currentUser
    var currentScreen: MutableState<Screen> = mutableStateOf(
        if(currentUser!= null) Screen.HomeScreen
        else Screen.SignInScreen
    )

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}
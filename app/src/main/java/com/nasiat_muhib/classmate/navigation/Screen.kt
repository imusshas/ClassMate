package com.nasiat_muhib.classmate.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

sealed class Screen {
    data object SignInScreen: Screen()
    data object SignUpScreen: Screen()
    data object ForgotPasswordScreen: Screen()

    data object HomeScreen: Screen()
    data object CreateSemesterScreen: Screen()
    data object EnrollCourseScreen: Screen()
    data object NotificationScreen: Screen()
    data object MenuScreen: Screen()
    data object CreateCourse: Screen()

    data object SearchTeacher: Screen()

}

object ClassMateAppRouter {
    private val currentUser = Firebase.auth.currentUser
    var currentScreen: MutableState<Screen> = mutableStateOf(
        if(currentUser != null) Screen.HomeScreen
        else Screen.SignInScreen
    )

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}
package com.nasiat_muhib.classmate.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.presentation.auth.forgot_password.ForgotPasswordScreen
import com.nasiat_muhib.classmate.presentation.auth.sign_in.SignInScreen
import com.nasiat_muhib.classmate.presentation.auth.sign_up.SignUpScreen
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterScreen
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourse
import com.nasiat_muhib.classmate.presentation.main.home.HomeScreen

@Composable
fun ClassMateSecondVersion() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Crossfade(targetState = ClassMateAppRouter.currentScreen, label = "") {
            when(it.value) {
                Screen.SignInScreen -> {
                    SignInScreen()
                }
                Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                Screen.ForgotPasswordScreen -> {
                    ForgotPasswordScreen()
                }

                Screen.HomeScreen -> {
                    HomeScreen()
                }

                Screen.CreateSemesterScreen -> {
                    CreateSemesterScreen()
                }

                Screen.CreateCourse -> {
                    CreateCourse()
                }

                Screen.EnrollCourseScreen -> TODO()
                Screen.MenuScreen -> TODO()
                Screen.NotificationScreen -> TODO()
            }
        }
    }
}
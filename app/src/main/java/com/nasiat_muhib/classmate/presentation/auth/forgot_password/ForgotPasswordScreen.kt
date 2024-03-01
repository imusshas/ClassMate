package com.nasiat_muhib.classmate.presentation.auth.forgot_password

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import com.nasiat_muhib.classmate.navigation.SystemBackButtonHandler

@Composable
fun ForgotPasswordScreen() {
    Text(text = "Forgot Password Screen")

    SystemBackButtonHandler {
        ClassMateAppRouter.navigateTo(Screen.SignInScreen)
    }
}
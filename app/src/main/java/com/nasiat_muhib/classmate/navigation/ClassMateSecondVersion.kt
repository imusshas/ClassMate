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
            }
        }
    }
}
package com.nasiat_muhib.classmate.presentation.auth.sign_up

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateBackToSignInScreen: () -> Unit,
) {
    val signUpDataState = signUpViewModel.signUpDataState.collectAsState()

    if (signUpDataState.value.data != null) {
        navigateToHomeScreen()
    }

    when (signUpDataState.value) {
        DataState.Loading -> {
            LoadingScreen()
        }
        is DataState.Error -> {
            SignUpScreenContent(
                signUpViewModel = signUpViewModel,
                navigateBackToSignInScreen = navigateBackToSignInScreen,
            )
        }
        is DataState.Success -> {
            SignUpScreenContent(
                signUpViewModel = signUpViewModel,
                navigateBackToSignInScreen = navigateBackToSignInScreen,
            )
        }
    }
}
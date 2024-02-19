package com.nasiat_muhib.classmate.presentation.auth.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.presentation.auth.sign_in.components.SignInContent
import com.nasiat_muhib.classmate.utils.CustomToast

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
) {

    val uiState by viewModel.signInState.collectAsState()

    when (uiState) {
        ResponseState.Loading -> LoadingScreen()
        is ResponseState.Failure -> uiState.error?.let { CustomToast(message = it) }
        is ResponseState.Success -> SignInContent(
            signIn = {email, password ->
                     viewModel.signIn(email, password)
            },
            navigateToHomeScreen = {
                navigateToHomeScreen.invoke()
            },
            navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
            navigateToSignUpScreen = navigateToSignUpScreen
        )
    }
}


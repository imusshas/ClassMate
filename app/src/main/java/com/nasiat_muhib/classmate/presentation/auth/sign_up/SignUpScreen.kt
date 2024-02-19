package com.nasiat_muhib.classmate.presentation.auth.sign_up

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.presentation.auth.sign_up.components.SignUpContent
import com.nasiat_muhib.classmate.utils.CustomToast

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToSignInScreen: () -> Unit,
) {
    val uiState by viewModel.signUpState.collectAsState()

    when(uiState) {
        ResponseState.Loading -> LoadingScreen()
        is ResponseState.Failure -> uiState.error?.let { CustomToast(message = it) }
        is ResponseState.Success -> SignUpContent(
            signUp = {email, password, user ->
                     viewModel.signUp(email, password, user)
            },
            navigateToHomeScreen = navigateToHomeScreen,
            navigateToSignInScreen = navigateToSignInScreen
        )
    }
}
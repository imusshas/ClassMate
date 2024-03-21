package com.nasiat_muhib.classmate.presentation.auth.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.presentation.auth.sign_in.components.SignInScreenContent

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    val signInDataState = signInViewModel.signInDataState.collectAsState()

    when(signInDataState.value) {
        is DataState.Error ->  {
            SignInScreenContent(signInViewModel = signInViewModel, error = signInDataState.value.error)
        }
        DataState.Loading ->  {
            LoadingScreen()
        }
        is DataState.Success ->  {
            SignInScreenContent(signInViewModel = signInViewModel)
        }
    }
}
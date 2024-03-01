package com.nasiat_muhib.classmate.presentation.main.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.presentation.auth.sign_up.SignUpViewModel

@Composable
fun HomeScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    CustomElevatedButton(
        text = "Sign Out",
        onClick = {
            signUpViewModel.signOut()
        }

    )
}
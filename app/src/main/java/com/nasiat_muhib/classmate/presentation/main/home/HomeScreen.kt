package com.nasiat_muhib.classmate.presentation.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.auth.sign_up.SignUpViewModel
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow

@Composable
fun HomeScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    Column {
        ClassMateTabRow(tab = TabItem.Home)
        CustomElevatedButton(
            text = "Sign Out",
            onClick = {
                signUpViewModel.signOut()
            }

        )
    }
}
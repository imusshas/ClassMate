package com.nasiat_muhib.classmate.presentation.main.menu.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.ErrorScreen
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.menu.profile.components.ProfileContent

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigateBackToMenuScreen: () -> Unit
) {

    val uiState by profileViewModel.userState.collectAsState()

    when(uiState) {
        ResponseState.Loading -> LoadingScreen()
        is ResponseState.Success -> uiState.data?.let { userData ->
            ProfileContent (
                profileViewModel = profileViewModel,
                user = userData,
                navigateBackToMenuScreen = navigateBackToMenuScreen
            )
        }
        is ResponseState.Failure -> uiState.error?.let { ErrorScreen(message = it) }

    }

}
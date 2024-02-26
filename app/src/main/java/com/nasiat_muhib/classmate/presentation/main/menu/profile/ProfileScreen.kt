package com.nasiat_muhib.classmate.presentation.main.menu.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        is ResponseState.Failure -> uiState.error?.let {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = it)
            }
        }

    }

}
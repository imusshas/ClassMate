package com.nasiat_muhib.classmate.presentation.main.menu.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.presentation.main.menu.profile.components.ProfileContent

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {

    val userState by profileViewModel.userState.collectAsState()

    when (userState) {
        is DataState.Error -> TODO()
        DataState.Loading -> {
            LoadingScreen()
        }
        is DataState.Success -> {
            userState.data?.let {
                ProfileContent(profileViewModel = profileViewModel, user = it)
            }
        }
    }

}
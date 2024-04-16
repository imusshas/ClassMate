package com.nasiat_muhib.classmate.presentation.main.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.NavigationViewModel
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.home.components.HomeScreenContent
import com.nasiat_muhib.classmate.strings.TAG

@Composable
fun HomeScreen(
    recomposeHomeScreen: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel,
    navigateToTab: (TabItem) -> Unit,
    navigateToCourseDetailsDisplay: () -> Unit
) {

    val userState by homeViewModel.userState.collectAsState()

    when (userState) {
        is DataState.Error -> {
            TODO()
        }
        DataState.Loading -> {
            LoadingScreen()
        }
        is DataState.Success -> {
//            Log.d(TAG, "HomeScreen: ${userState.data}")
            userState.data?.let { user ->
                homeViewModel.getCourseList(user.courses)
                homeViewModel.getRequestedCourseList(user.requestedCourses)
                homeViewModel.getClassDetails(user.courses)
                homeViewModel.getAllPosts()
                homeViewModel.getUserPosts()
                try {
                    val token = Firebase.messaging.token.result
                    if (token != null) {
                    homeViewModel.updateToken(token)
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "HomeScreen: ${e.message}")
                }
                HomeScreenContent(
                    homeViewModel = homeViewModel,
                    navigationViewModel = navigationViewModel,
                    user = user,
                    navigateToTab = navigateToTab,
                    navigateToCourseDetailsDisplay = navigateToCourseDetailsDisplay,
                    recomposeHomeScreen = recomposeHomeScreen
                )
            }
        }
    }

}
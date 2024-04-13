package com.nasiat_muhib.classmate.presentation.main.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.NavigationViewModel
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.home.components.HomeScreenContent
import com.nasiat_muhib.classmate.strings.TAG

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel,
    navigateToTab: (TabItem) -> Unit,
    navigateToCourseDetailsDisplay: () -> Unit
) {
    homeViewModel.getUser()
    val userState by homeViewModel.userState.collectAsState()

    when (userState) {
        is DataState.Error -> TODO()
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
                HomeScreenContent(
                    homeViewModel = homeViewModel,
                    navigationViewModel = navigationViewModel,
                    user = user,
                    navigateToTab = navigateToTab,
                    navigateToCourseDetailsDisplay = navigateToCourseDetailsDisplay
                )
            }
        }
    }

}
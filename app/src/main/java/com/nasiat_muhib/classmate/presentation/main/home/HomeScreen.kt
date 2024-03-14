package com.nasiat_muhib.classmate.presentation.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.home.components.HomeScreenContent

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    val userState by homeViewModel.userState.collectAsState()

    when (userState) {
        is DataState.Error -> TODO()
        DataState.Loading -> {
            LoadingScreen()
        }
        is DataState.Success -> {
            userState.data?.let {
                homeViewModel.getTodayClasses()
                homeViewModel.getTomorrowClasses()
                homeViewModel.getCourseList(it.courses)
                homeViewModel.getRequestedCourseList(it.requestedCourses)
                
                HomeScreenContent(homeViewModel = homeViewModel)
            }
        }
    }

}
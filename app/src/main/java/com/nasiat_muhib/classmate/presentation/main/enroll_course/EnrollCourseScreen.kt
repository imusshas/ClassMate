package com.nasiat_muhib.classmate.presentation.main.enroll_course

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.NavigationViewModel
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.enroll_course.components.EnrollCourseScreenContent
import com.nasiat_muhib.classmate.presentation.main.enroll_course.components.SearchCourseScreen
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun EnrollCourseScreen(
    enrollCourseViewModel: EnrollCourseViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel,
    navigateToTab: (TabItem) -> Unit,
    navigateToCourseDetailsDisplay: () -> Unit
) {

    enrollCourseViewModel.getUser()
    val userState by enrollCourseViewModel.userState.collectAsState()

    when (userState) {
        is DataState.Error -> {
            TODO()
        }
        DataState.Loading -> {
            LoadingScreen()
        }

        is DataState.Success -> {
            userState.data?.let {
                EnrollCourseScreenContent(
                    enrollCourseViewModel,
                    navigationViewModel = navigationViewModel,
                    user = it,
                    navigateToTab = navigateToTab,
                    navigateToCourseDetailsDisplay = navigateToCourseDetailsDisplay
                )
            }
        }
    }
}
package com.nasiat_muhib.classmate.presentation.main.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.NavigationViewModel
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow

@Composable
fun NotificationScreen(
    notificationViewModel: NotificationViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel,
    navigateToHomeScreen: () -> Unit,
    navigateToCourseDetailsScreen: () -> Unit,
    navigateToTab: (TabItem) -> Unit,
) {

    notificationViewModel.getUser()
    val userState by notificationViewModel.userState.collectAsState()

    when (userState) {
        is DataState.Error -> {
            TODO()
        }
        DataState.Loading -> {
            LoadingScreen()
        }
        is DataState.Success -> {
            userState.data?.let {
                notificationViewModel.getNotifications(it.email)
                notificationViewModel.getCourses(it.courses)
                NotificationContent(
                    navigateToHomeScreen = navigateToHomeScreen,
                    navigateToCourseDetailsScreen = navigateToCourseDetailsScreen,
                    navigateToTab = navigateToTab,
                    notificationViewModel = notificationViewModel,
                    navigationViewModel = navigationViewModel,
                    user = it
                )
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ClassMateTabRow(tab = TabItem.Notification, navigateToTab = navigateToTab)
    }
}
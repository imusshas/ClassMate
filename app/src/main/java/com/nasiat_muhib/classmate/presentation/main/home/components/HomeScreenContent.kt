package com.nasiat_muhib.classmate.presentation.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.TwoTitleContainer
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.HomeUIEvent
import com.nasiat_muhib.classmate.navigation.NavigationViewModel
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.home.HomeViewModel
import com.nasiat_muhib.classmate.strings.STUDENT
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun HomeScreenContent(
    recomposeHomeScreen: () -> Unit,
    homeViewModel: HomeViewModel,
    navigationViewModel: NavigationViewModel,
    user: User,
    navigateToTab: (TabItem) -> Unit,
    navigateToCourseDetailsDisplay: () -> Unit,
) {
    homeViewModel.getToken(user.email)
    homeViewModel.getTodayAndTomorrowClassesClasses()
    val todayClasses by homeViewModel.todayClasses.collectAsState()
    val tomorrowClasses by homeViewModel.tomorrowClasses.collectAsState()

    val todayOrTomorrow = rememberSaveable { mutableStateOf(true) }

    val courses by homeViewModel.courses.collectAsState()
    val requestedCourses by homeViewModel.requestedCourses.collectAsState()

    val courseOrRequest = rememberSaveable { mutableStateOf(true) }

    homeViewModel.getAllPosts()
    homeViewModel.getUserPosts()
    val posts by homeViewModel.userPost.collectAsState()
    val createPostDialogState by homeViewModel.createPostDialogState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ClassMateTabRow(tab = TabItem.Home, navigateToTab = navigateToTab)
        Spacer(modifier = Modifier.height(SmallSpace))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MediumSpace)
        ) {

            TwoTitleContainer(
                leftTitle = "Today's Classes",
                rightTitle = "Tomorrow's Classes",
                leftClick = { todayOrTomorrow.value = true },
                rightClick = { todayOrTomorrow.value = false }
            )

            FixedHeightLazyColumn(items = if (todayOrTomorrow.value) todayClasses else tomorrowClasses) {
                ClassDisplay(
                    homeViewModel = homeViewModel,
                    classDetails = it,
                    user = user,
                    recomposeHomeScreen = recomposeHomeScreen
                )
            }

            TwoTitleContainer(
                leftTitle = "Your Courses",
                rightTitle = "Requested Courses",
                leftClick = {
                    homeViewModel.getCourseList(user.courses)
                    courseOrRequest.value = true
                },
                rightClick = {
                    homeViewModel.getRequestedCourseList(user.requestedCourses)
                    courseOrRequest.value = false
                }
            )

            FixedHeightSwipeAbleLazyColumn(items = if (courseOrRequest.value) courses else requestedCourses,
                key = {
                    "${it.hashCode()}"
                }) {
                CourseDisplay(
                    course = it,
                    homeViewModel = homeViewModel,
                    navigationViewModel = navigationViewModel,
                    isRequested = requestedCourses.contains(it),
                    isVisible = if (courseOrRequest.value) courses.contains(it) else requestedCourses.contains(it),
                    navigateToCourseDetailsDisplay = navigateToCourseDetailsDisplay
                )
            }

            Spacer(modifier = Modifier.height(LargeSpace))
            if (user.role != STUDENT) {
                CustomElevatedButton(
                    text = "Create Post",
                    onClick = { homeViewModel.onHomeEvent(HomeUIEvent.PostButtonClicked) })
            }
            Spacer(modifier = Modifier.height(MediumSpace))
            posts.forEach {
                PostDisplay(
                    recomposeHomeScreen = recomposeHomeScreen,
                    post = it,
                    homeViewModel = homeViewModel,
                    isCreator = it.creator == user.email
                )
            }

        }

        if (createPostDialogState) {
            CreatePostDialog(
                homeViewModel = homeViewModel,
                user = user,
                recomposeHomeScreen = recomposeHomeScreen
            )
        }
    }
}
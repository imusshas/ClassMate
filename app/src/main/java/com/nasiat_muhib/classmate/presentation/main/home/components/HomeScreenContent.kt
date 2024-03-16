package com.nasiat_muhib.classmate.presentation.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.components.CustomLazyColumn
import com.nasiat_muhib.classmate.components.CustomSwipeAbleLazyColumn
import com.nasiat_muhib.classmate.components.TwoTitleContainer
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.home.HomeViewModel
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun HomeScreenContent(
    homeViewModel: HomeViewModel,
    user: User
) {

    homeViewModel.getTodayClasses()
    homeViewModel.getTomorrowClasses()
    homeViewModel.getClassDetails()
    homeViewModel.getCourseList(user.courses)
    homeViewModel.getRequestedCourseList(user.requestedCourses)

    val todayClasses by homeViewModel.todayClasses.collectAsState()
    val tomorrowClasses by homeViewModel.tomorrowClasses.collectAsState()
    val todayOrTomorrow = rememberSaveable { mutableStateOf(true) }

    val courses by homeViewModel.courses.collectAsState()
    val requestedCourses by homeViewModel.requestedCourses.collectAsState()

    val courseOrRequest = rememberSaveable {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ClassMateTabRow(tab = TabItem.Home)
        Spacer(modifier = Modifier.heightIn(SmallSpace))
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

            CustomLazyColumn(items = if (todayOrTomorrow.value) todayClasses else tomorrowClasses) {
                ClassDisplay(homeViewModel = homeViewModel, classDetails = it)
            }

            TwoTitleContainer(
                leftTitle = "Your Courses",
                rightTitle = "Requested Courses",
                leftClick = {
                    courseOrRequest.value = true
                },
                rightClick = {
                    courseOrRequest.value = false
                }
            )

            CustomSwipeAbleLazyColumn(items = if (courseOrRequest.value) courses else requestedCourses,
                key = {
                    "${it.courseDepartment}:${it.courseCode}"
                }) {
                CourseDisplay(
                    course = it,
                    homeViewModel = homeViewModel,
                    isRequested = requestedCourses.contains(it)
                )
            }

        }

        ElevatedButton(onClick = {
            homeViewModel.signOut()
        }) {
            Text(text = "Sign Out")
        }
    }
}
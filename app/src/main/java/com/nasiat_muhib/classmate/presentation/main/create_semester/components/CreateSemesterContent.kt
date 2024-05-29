package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.components.CustomSwipeAbleLazyColumn
import com.nasiat_muhib.classmate.components.TwoTitleContainer
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.navigation.NavigationViewModel
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterViewModel
import com.nasiat_muhib.classmate.strings.ADD_ICON
import com.nasiat_muhib.classmate.strings.CLASS_REPRESENTATIVE
import com.nasiat_muhib.classmate.strings.CREATED_COURSES_TITLE
import com.nasiat_muhib.classmate.strings.PENDING_COURSES_TITLE
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateSemesterContent(
    createSemesterViewModel: CreateSemesterViewModel,
    navigationViewModel: NavigationViewModel,
    user: User,
    navigateToTab: (TabItem) -> Unit,
    navigateToCreateCourse: () -> Unit,
    navigateToCourseDetailsDisplay: () -> Unit
) {

    createSemesterViewModel.getCreatedCourses(user.courses, user.email)
    createSemesterViewModel.getPendingCourses(user.courses, user.email)

    val createdCourses by createSemesterViewModel.cratedCourses.collectAsState()
    val pendingCourses by createSemesterViewModel.pendingCourses.collectAsState()

    val createdOrPending = rememberSaveable { mutableStateOf(true) }

    Scaffold(
        topBar = { ClassMateTabRow(
            tab = TabItem.CreateSemester,
            navigateToTab =navigateToTab
        ) },
        floatingActionButton = {
            if (user.role == CLASS_REPRESENTATIVE) {
                FloatingActionButton(
                    onClick = {
                        navigateToCreateCourse()
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = ADD_ICON,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() + MediumSpace),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SmallSpace)
        ) {
            // TODO: Add Routine
            // TODO: Add Already Created Courses
            TwoTitleContainer(
                leftTitle = CREATED_COURSES_TITLE,
                rightTitle = PENDING_COURSES_TITLE,
                leftClick = {
                    createSemesterViewModel.getCreatedCourses(user.courses, user.email)
                    createdOrPending.value = true
                            },
                rightClick = {
                    createSemesterViewModel.getPendingCourses(user.courses, user.email)
                    createdOrPending.value = false }
            )

            CustomSwipeAbleLazyColumn(
                items = if (createdOrPending.value) createdCourses else pendingCourses,
                key = {
                      "${it.hashCode()}"
                },
                modifier = Modifier.fillMaxHeight()
            ) {
                DisplayCourse(
                    course = it,
                    createSemesterViewModel = createSemesterViewModel,
                    navigationViewModel = navigationViewModel,
                    isVisible = if (createdOrPending.value) createdCourses.contains(it) else pendingCourses.contains(it),
                    navigateToCourseDetailsDisplay = navigateToCourseDetailsDisplay
                )
            }

        }
    }
}
package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import com.nasiat_muhib.classmate.components.CustomSwipeAbleLazyColumn
import com.nasiat_muhib.classmate.components.TwoTitleContainer
import com.nasiat_muhib.classmate.domain.event.CreateSemesterUIEvent
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterViewModel
import com.nasiat_muhib.classmate.strings.ADD_ICON
import com.nasiat_muhib.classmate.strings.CLASS_REPRESENTATIVE
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateSemesterContent(
    createSemesterViewModel: CreateSemesterViewModel,
) {

    val user by createSemesterViewModel.userState.collectAsState()
    val createdCourses by createSemesterViewModel.cratedCourses.collectAsState()
    val pendingCourses by createSemesterViewModel.pendingCourses.collectAsState()

    val courses = rememberSaveable { mutableStateOf(createdCourses) }

    Scaffold(
        topBar = { ClassMateTabRow(tab = TabItem.CreateSemester) },
        floatingActionButton = {
            if (user.data?.role == CLASS_REPRESENTATIVE) {
                FloatingActionButton(
                    onClick = {
                        createSemesterViewModel.onCreateSemesterEvent(CreateSemesterUIEvent.CreateSemesterFABClick)
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
                leftTitle = "Created Courses",
                rightTitle = "Pending Courses",
                leftClick = { courses.value = createdCourses },
                rightClick = { courses.value = pendingCourses }
            )

            CustomSwipeAbleLazyColumn(
                items = courses.value,
                key = {
                    "${it.courseDepartment}:${it.courseCode}"
                }
            ) {
//            courses.value.forEach {
                DisplayCourse(course = it, createSemesterViewModel = createSemesterViewModel)
//            }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateCourseContentPreview() {
//    CreateSemesterContent(user = User(), createSemesterViewModel = null)
}
package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nasiat_muhib.classmate.components.SwipeToDeleteOrEditContainer
import com.nasiat_muhib.classmate.components.TitleContainer
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.CreateSemesterUIEvent
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterViewModel
import com.nasiat_muhib.classmate.strings.ADD_ICON
import com.nasiat_muhib.classmate.strings.CLASS_REPRESENTATIVE
import com.nasiat_muhib.classmate.strings.CREATED_COURSES
import com.nasiat_muhib.classmate.strings.STUDENT
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateSemesterContent(
    createSemesterViewModel: CreateSemesterViewModel,
) {

    val course by createSemesterViewModel.courses.collectAsState()

    Scaffold(
        topBar = { ClassMateTabRow(tab = TabItem.CreateSemester) },
        floatingActionButton = {
//            if (user.role == CLASS_REPRESENTATIVE) {
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
//            }
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() + MediumSpace),
            verticalArrangement = Arrangement.spacedBy(MediumSpace),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TODO: Add Routine
            // TODO: Add Already Created Courses
            TitleContainer(title = CREATED_COURSES)

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MediumSpace),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = course) {item ->
                    SwipeToDeleteOrEditContainer(
                        item = item,
                        onDelete = {

                        },
                        onEdit = {

                        }
                    ) { currentCourse ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(NormalHeight)
                                .padding(horizontal = MediumSpace),
                            shape = LargeRounded
                        ) {
                            Row (
                                modifier = Modifier.fillMaxSize().padding(horizontal = SmallSpace),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = currentCourse.courseCode)
                                Text(text = currentCourse.courseTitle)
                                Text(text = currentCourse.courseCredit.toString())
                            }
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateCourseContentPreview() {
//    CreateSemesterContent(user = User(), createSemesterViewModel = null)
}
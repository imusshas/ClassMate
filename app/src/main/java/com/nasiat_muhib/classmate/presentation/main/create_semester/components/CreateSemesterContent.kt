package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.CreateSemesterUIEvent
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterViewModel
import com.nasiat_muhib.classmate.strings.ADD_ICON
import com.nasiat_muhib.classmate.strings.CLASS_REPRESENTATIVE
import com.nasiat_muhib.classmate.ui.theme.MediumSpace

@Composable
fun CreateSemesterContent(
    user: User,
    createSemesterViewModel: CreateSemesterViewModel,
) {

    val course by createSemesterViewModel.courses.collectAsState()
    Scaffold(
        topBar = { ClassMateTabRow(tab = TabItem.CreateSemester) },
        floatingActionButton = {
            if (user.role == CLASS_REPRESENTATIVE) {
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
                .padding(top = paddingValues.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(MediumSpace),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TODO: Add Routine
            // TODO: Add Already Created Courses
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumSpace)
            ) {
                items(course) {
                    Text(text = it.courseCode)
                }
            }
        }
    }
}
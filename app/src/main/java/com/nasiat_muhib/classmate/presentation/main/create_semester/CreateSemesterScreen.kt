package com.nasiat_muhib.classmate.presentation.main.create_semester


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nasiat_muhib.classmate.domain.event.CreateSemesterUIEvent
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.strings.ADD_ICON
import com.nasiat_muhib.classmate.ui.theme.ExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace

@Composable
fun CreateSemesterScreen(
    createSemesterViewModel: CreateSemesterViewModel = viewModel()
) {

    Scaffold(
        topBar = { ClassMateTabRow(tab = TabItem.CreateSemester) },
        floatingActionButton = {
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

    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(MediumSpace),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: Add Routine
           // TODO: Add Already Created Courses
        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CreateSemesterScreenPreview() {
    CreateSemesterScreen()
}
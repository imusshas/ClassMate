package com.nasiat_muhib.classmate.presentation.main.create_semester

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nasiat_muhib.classmate.components.CustomDatePicker
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomTimePicker
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateSemesterViewModel
import com.nasiat_muhib.classmate.strings.ADD

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateSemesterScreen(
    createSemesterViewModel: CreateSemesterViewModel = viewModel()
) {

    Scaffold(
        topBar = { ClassMateTabRow(tab = TabItem.CreateSemester) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = MaterialTheme.colorScheme.background
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = ADD,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTimePicker({}, {}, {})
            CustomDatePicker(onDayChange = {}, onMonthChange = {}, onYearChange = {})
            CustomElevatedButton(text = "Increment", onClick = {
                /*TODO*/
            })

            CustomElevatedButton(text = "Create Course", onClick = {
                ClassMateAppRouter.navigateTo(Screen.CreateCourse)
            })
        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CreateSemesterScreenPreview() {
    CreateSemesterScreen()
}
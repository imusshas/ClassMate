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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.CustomDatePicker
import com.nasiat_muhib.classmate.components.CustomTimePicker
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.strings.ADD

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateSemesterScreen() {

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTimePicker({}, {}, {})
            CustomDatePicker(onDayChange = {}, onMonthChange = {}, onYearChange = {})
        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CreateSemesterScreenPreview() {
    CreateSemesterScreen()
}
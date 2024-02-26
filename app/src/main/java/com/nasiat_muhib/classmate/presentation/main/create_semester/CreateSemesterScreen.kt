package com.nasiat_muhib.classmate.presentation.main.create_semester

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.ClassMateAppScreen
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseComponent

@Composable
fun CreateSemesterScreen(navigateToTab: (TabItem) -> Unit) {

    var showWhat by rememberSaveable { mutableStateOf(true) }
    var showSheet by rememberSaveable { mutableStateOf(false) }

    var code by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var teacher by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var classroom by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        ClassMateAppScreen(
            tab = TabItem.CreateSemester,
            navigateToTab = { tabItem -> navigateToTab.invoke(tabItem) })

       CreateCourseComponent(
           showWhat = showWhat,
           showSheet = showSheet,
           code = code,
           title = title,
           teacher = teacher,
           password = password,
           classroom = classroom,
           onCreateCourseClick = { showWhat = false },
           onCodeChange = {code = it},
           onTitleChange = {title = it},
           onTeacherChange = {teacher = it},
           onPasswordChange = {password = it},
           onClassroomChange = {classroom = it},
           onCreateClassClick = { showSheet = true },
           onDismissRequest = {showSheet = false},
           onDoneClick = { showWhat = true },
           modifier = Modifier
       )
    }
}
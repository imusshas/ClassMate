package com.nasiat_muhib.classmate.presentation.main.enroll_course

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow

@Composable
fun EnrollCourseScreen() {
    Column (modifier = Modifier.fillMaxSize()) {
        ClassMateTabRow(tab = TabItem.EnrollCourse)
        SearchCourseScreen()
    }
}
package com.nasiat_muhib.classmate.presentation.main.enroll_course

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.search.SearchUI

@Composable
fun EnrollCourseScreen() {
    Column {
        ClassMateTabRow(tab = TabItem.EnrollCourse)
        SearchUI()
    }
}
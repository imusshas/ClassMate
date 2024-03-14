package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.ui.theme.MediumSpace

@Composable
fun CourseDetailsDisplay(course: Course) {

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MediumSpace)
    ) {

        CourseDetailsDisplayTopBar(
            courseCode = course.courseCode,
            onBackIconClick = { /*TODO*/ }
        )

    }

}
package com.nasiat_muhib.classmate.presentation.main.enroll_course.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.components.CustomSwipeAbleLazyColumn
import com.nasiat_muhib.classmate.components.TitleContainer
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.enroll_course.EnrollCourseViewModel
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun EnrollCourseScreenContent(
    enrollCourseViewModel: EnrollCourseViewModel,
    user: User
) {

    enrollCourseViewModel.getAlreadyEnrolledCourses()
    val alreadyEnrolledCourses by enrollCourseViewModel.alreadyEnrolled.collectAsState()

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClassMateTabRow(tab = TabItem.EnrollCourse)
        Column (modifier = Modifier.weight(1f)) {
            SearchCourseScreen(user = user)
        }
        Spacer(modifier = Modifier.height(LargeSpace))
        Column (modifier = Modifier.weight(1f)) {
            TitleContainer(title = "Already Enrolled")
            Spacer(modifier = Modifier.height(SmallSpace))
            CustomSwipeAbleLazyColumn(
                items = alreadyEnrolledCourses,
                key = {
                    "${it.courseDepartment}:${it.courseCode}"
                }
            ) {
                CourseDisplayOnEnrollCourse(course = it, enrollCourseViewModel = enrollCourseViewModel)
            }
        }
    }
}
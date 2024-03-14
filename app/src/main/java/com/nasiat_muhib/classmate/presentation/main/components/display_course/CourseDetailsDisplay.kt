package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.ClickableTitleContainer
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.event.CourseDetailsDisplayUIEvent
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace

@Composable
fun CourseDetailsDisplay(
    course: Course,
    courseDetailsDisplayViewModel: CourseDetailsDisplayViewModel = hiltViewModel()
) {

    courseDetailsDisplayViewModel.setCurrentCourse(course)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        CourseDetailsDisplayTopBar(
            courseCode = course.courseCode,
            onBackIconClick = {
                courseDetailsDisplayViewModel.onDisplayEvent(CourseDetailsDisplayUIEvent.CourseDetailsDisplayTopBarBackButtonClicked)
            }
        )

        BlackBoardContent(course = course)

        Spacer(modifier = Modifier.height(LargeSpace))

        ClickableTitleContainer(
            title = "Classes +",
            onTitleClick = { /*TODO*/ }
        )

        Spacer(modifier = Modifier.height(MediumSpace))

        ClickableTitleContainer(
            title = "Term Test +",
            onTitleClick = { /*TODO*/ }
        )

        Spacer(modifier = Modifier.height(MediumSpace))

        ClickableTitleContainer(
            title = "Assignment +",
            onTitleClick = { /*TODO*/ }
        )

        ClickableTitleContainer(
            title = "Resource Link +",
            onTitleClick = { /*TODO*/ }
        )

//        CustomSwipeAbleLazyColumn(
//            items = ,
//            key =
//        ) {
//
//        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun CourseDisplayPreview() {

    CourseDetailsDisplay(course = Course())
}
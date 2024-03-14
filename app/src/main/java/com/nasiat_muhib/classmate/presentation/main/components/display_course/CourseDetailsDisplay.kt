package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.ClickableTitleContainer
import com.nasiat_muhib.classmate.components.CustomSwipeAbleLazyColumn
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.event.CourseDetailsDisplayUIEvent
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CourseDetailsDisplay(
    course: Course,
    courseDetailsDisplayViewModel: CourseDetailsDisplayViewModel = hiltViewModel()
) {

    courseDetailsDisplayViewModel.setCurrentCourse(course)
    courseDetailsDisplayViewModel.getClassDetailsList()
    val classes by courseDetailsDisplayViewModel.classes.collectAsState()
    val createClassDialogState by courseDetailsDisplayViewModel.createClassDialogState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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
            onTitleClick = {
                courseDetailsDisplayViewModel.onDisplayEvent(CourseDetailsDisplayUIEvent.ClassTitleClicked)
            }
        )
        Spacer(modifier = Modifier.height(SmallSpace))
        CustomSwipeAbleLazyColumn(
            items = classes,
            key = {
                "${it.classDepartment}:${it.classCourseCode}:${it.classNo}"
            }
        ) {
            DisplayClass(classDetails = it, courseDetailsDisplayViewModel = courseDetailsDisplayViewModel)
        }

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

        Spacer(modifier = Modifier.height(MediumSpace))

        ClickableTitleContainer(
            title = "Resource Link +",
            onTitleClick = { /* TODO */ }
        )

//        CustomSwipeAbleLazyColumn(
//            items = ,
//            key =
//        ) {
//
//        }

        if (createClassDialogState) {
            CreateClassOnDisplay(courseDetailsDisplayViewModel = courseDetailsDisplayViewModel)
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun CourseDisplayPreview() {

    CourseDetailsDisplay(course = Course())
}
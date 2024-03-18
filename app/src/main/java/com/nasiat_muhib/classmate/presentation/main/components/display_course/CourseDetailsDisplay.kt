package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import com.nasiat_muhib.classmate.components.ClickableTitleContainerWithIcon
import com.nasiat_muhib.classmate.components.CustomSwipeAbleLazyColumn
import com.nasiat_muhib.classmate.components.TitleContainer
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.event.CourseDetailsDisplayUIEvent
import com.nasiat_muhib.classmate.navigation.Screen
import com.nasiat_muhib.classmate.strings.ADD_ASSIGNMENT_TITLE
import com.nasiat_muhib.classmate.strings.ADD_CLASS_TITLE
import com.nasiat_muhib.classmate.strings.ADD_TERM_TEST_TITLE
import com.nasiat_muhib.classmate.strings.ASSIGNMENT_TITLE
import com.nasiat_muhib.classmate.strings.CLASS_TITLE
import com.nasiat_muhib.classmate.strings.RESOURCE_LINK_TITLE
import com.nasiat_muhib.classmate.strings.TERM_TEST_TITLE
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CourseDetailsDisplay(
    course: Course,
    screen: Screen,
    courseDetailsDisplayViewModel: CourseDetailsDisplayViewModel = hiltViewModel(),
) {

    courseDetailsDisplayViewModel.setCurrentCourse(course)
    courseDetailsDisplayViewModel.getClassDetailsList()
    courseDetailsDisplayViewModel.getTermTestsList()
    courseDetailsDisplayViewModel.getAssignmentList()
    courseDetailsDisplayViewModel.getResourceList()

    val userState by courseDetailsDisplayViewModel.currentUser.collectAsState()

    val classes by courseDetailsDisplayViewModel.classes.collectAsState()
    val createClassDialogState by courseDetailsDisplayViewModel.createClassDialogState.collectAsState()

    val termTests by courseDetailsDisplayViewModel.termTests.collectAsState()
    val createTermTestDialogState by courseDetailsDisplayViewModel.createTermTestDialogState.collectAsState()

    val assignments by courseDetailsDisplayViewModel.assignments.collectAsState()
    val createAssignmentDialogState by courseDetailsDisplayViewModel.createAssignmentDialogState.collectAsState()

    val resources by courseDetailsDisplayViewModel.resources.collectAsState()
    val createResourceDialogState by courseDetailsDisplayViewModel.createResourceDialogState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        CourseDetailsDisplayTopBar(
            courseCode = course.courseCode,
            onBackIconClick = {
                courseDetailsDisplayViewModel.onDisplayEvent(CourseDetailsDisplayUIEvent.CourseDetailsDisplayTopBarBackButtonClicked(screen))
            }
        )

        BlackBoardContent(course = course)

        Spacer(modifier = Modifier.height(LargeSpace))

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (userState.data != null && (userState.data!!.email == course.courseTeacher || userState.data!!.email == course.courseCreator)) {
                ClickableTitleContainer(
                    title = ADD_CLASS_TITLE,
                    onTitleClick = {
                        courseDetailsDisplayViewModel.onDisplayEvent(CourseDetailsDisplayUIEvent.TermTestTitleClicked)
                    }
                )
            } else {
                TitleContainer(title = CLASS_TITLE)
            }

            Spacer(modifier = Modifier.height(SmallSpace))
            CustomSwipeAbleLazyColumn(
                items = classes,
                key = {
                    "${it.classDepartment}:${it.classCourseCode}:${it.classNo}"
                }
            ) {
                DisplayClass(
                    classDetails = it,
                    courseDetailsDisplayViewModel = courseDetailsDisplayViewModel,
                    isSwipeAble = course.courseCreator == userState.data?.email || course.courseTeacher == userState.data?.email
                )
            }

            Spacer(modifier = Modifier.height(MediumSpace))
            if (userState.data != null && (userState.data!!.email == course.courseTeacher || userState.data!!.email == course.courseCreator)) {
                ClickableTitleContainer(
                    title = ADD_TERM_TEST_TITLE,
                    onTitleClick = {
                        courseDetailsDisplayViewModel.onDisplayEvent(CourseDetailsDisplayUIEvent.TermTestTitleClicked)
                    }
                )
            } else {
                TitleContainer(title = TERM_TEST_TITLE)
            }

            Spacer(modifier = Modifier.height(SmallSpace))
            CustomSwipeAbleLazyColumn(
                items = termTests,
                key = {
                    "${it.department}:${it.courseCode}:${it.eventNo}"
                }
            ) {
                DisplayEvent(
                    event = it,
                    courseDetailsDisplayViewModel = courseDetailsDisplayViewModel,
                    isSwipeAble = course.courseCreator == userState.data?.email || course.courseTeacher == userState.data?.email
                )
            }


            Spacer(modifier = Modifier.height(MediumSpace))
            if (userState.data != null && (userState.data!!.email == course.courseTeacher || userState.data!!.email == course.courseCreator)) {
                ClickableTitleContainer(
                    title = ADD_ASSIGNMENT_TITLE,
                    onTitleClick = {
                        courseDetailsDisplayViewModel.onDisplayEvent(CourseDetailsDisplayUIEvent.TermTestTitleClicked)
                    }
                )
            } else {
                TitleContainer(title = ASSIGNMENT_TITLE)
            }

            Spacer(modifier = Modifier.height(SmallSpace))
            CustomSwipeAbleLazyColumn(
                items = assignments,
                key = {
                    "${it.department}:${it.courseCode}:${it.eventNo}"
                }
            ) {
                DisplayEvent(
                    event = it,
                    courseDetailsDisplayViewModel = courseDetailsDisplayViewModel,
                    isSwipeAble = course.courseCreator == userState.data?.email || course.courseTeacher == userState.data?.email
                )
            }

            Spacer(modifier = Modifier.height(MediumSpace))

            if (userState.data != null && (userState.data!!.email == course.courseTeacher || userState.data!!.email == course.courseCreator)) {
                ClickableTitleContainerWithIcon(
                    title = RESOURCE_LINK_TITLE,
                    onTitleClick = {
                        courseDetailsDisplayViewModel.onDisplayEvent(CourseDetailsDisplayUIEvent.ResourceTitleClicked)
                    }
                )
            } else {
                TitleContainer(title = RESOURCE_LINK_TITLE)
            }
            
            Spacer(modifier = Modifier.height(SmallSpace))
            CustomSwipeAbleLazyColumn(
                items = resources, 
                key = {
                   "${it.resourceDepartment}:${it.resourceCourseCode}:${it.resourceNo}" 
                }
            ) {
                DisplayResourceLink(it)
            }
            
            Spacer(modifier = Modifier.heightIn(LargeSpace))
        }

        if (createClassDialogState) {
            CreateClassOnDisplay(courseDetailsDisplayViewModel = courseDetailsDisplayViewModel)
        }

        if (createTermTestDialogState) {
            CreateTermTest(courseDetailsDisplayViewModel = courseDetailsDisplayViewModel)
        }

        if (createAssignmentDialogState) {
            CreateAssignment(courseDetailsDisplayViewModel = courseDetailsDisplayViewModel)
        }

        if (createResourceDialogState) {
            CreateResourceLink(courseDetailsDisplayViewModel = courseDetailsDisplayViewModel)
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun CourseDisplayPreview() {
//    CourseDetailsDisplay(course = Course())
}
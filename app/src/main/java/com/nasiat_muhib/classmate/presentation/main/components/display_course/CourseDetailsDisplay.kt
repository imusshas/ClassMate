package com.nasiat_muhib.classmate.presentation.main.components.display_course

import android.util.Log
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
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.nasiat_muhib.classmate.components.ClickableTitleContainer
import com.nasiat_muhib.classmate.components.ClickableTitleContainerWithIcon
import com.nasiat_muhib.classmate.components.CustomSwipeAbleLazyColumn
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.components.TitleContainer
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.event.CourseDetailsDisplayUIEvent
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.ADD_ASSIGNMENT_TITLE
import com.nasiat_muhib.classmate.strings.ADD_CLASS_TITLE
import com.nasiat_muhib.classmate.strings.ADD_TERM_TEST_TITLE
import com.nasiat_muhib.classmate.strings.ASSIGNMENT_TITLE
import com.nasiat_muhib.classmate.strings.CLASS_TITLE
import com.nasiat_muhib.classmate.strings.RESOURCE_LINK_TITLE
import com.nasiat_muhib.classmate.strings.TAG
import com.nasiat_muhib.classmate.strings.TERM_TEST_TITLE
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CourseDetailsDisplay(
    courseDetailsDisplayViewModel: CourseDetailsDisplayViewModel = hiltViewModel(),
    course: Course,
    navigateBack: () -> Unit,
    recomposeCourseDetailsDisplay: () -> Unit
) {

    courseDetailsDisplayViewModel.updateToken()
    courseDetailsDisplayViewModel.getUser()
    courseDetailsDisplayViewModel.setCurrentCourse(course)
    courseDetailsDisplayViewModel.getCurrentCourse()
    courseDetailsDisplayViewModel.getClassDetailsList()
    courseDetailsDisplayViewModel.getTermTestsList()
    courseDetailsDisplayViewModel.getAssignmentList()
    courseDetailsDisplayViewModel.getResourceList()

    val userState by courseDetailsDisplayViewModel.currentUser.collectAsState()

    when (userState) {
        is DataState.Error -> {

        }
        DataState.Loading -> {
            LoadingScreen()
        }
        is DataState.Success -> {
            userState.data?.let {
                CourseDetailsDisplayContent(course = course, user = it, navigateBack = navigateBack, recomposeCourseDetailsDisplay = recomposeCourseDetailsDisplay)
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun CourseDisplayPreview() {
//    CourseDetailsDisplay(course = Course())
}
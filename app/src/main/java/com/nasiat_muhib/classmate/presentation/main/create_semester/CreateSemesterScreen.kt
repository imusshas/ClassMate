package com.nasiat_muhib.classmate.presentation.main.create_semester


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nasiat_muhib.classmate.components.ErrorScreen
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.NavigationViewModel
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateSemesterContent


@Composable
fun CreateSemesterScreen(
    createSemesterViewModel: CreateSemesterViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel,
    navigateToTab: (TabItem) -> Unit,
    navigateToCreateCourse: () -> Unit,
    navigateToCourseDetailsDisplay: () -> Unit
) {
    createSemesterViewModel.getUser()
    val userState by createSemesterViewModel.userState.collectAsState()
    when (userState) {

        is DataState.Error -> ErrorScreen(error = userState.error.toString())

        DataState.Loading -> LoadingScreen()

        is DataState.Success ->  {
            userState.data?.let {
                createSemesterViewModel.getCreatedCourses(it.courses, it.email)
                createSemesterViewModel.getPendingCourses(it.courses, it.email)
                CreateSemesterContent(
                    createSemesterViewModel = createSemesterViewModel,
                    navigationViewModel = navigationViewModel,
                    user = it,
                    navigateToTab = navigateToTab,
                    navigateToCreateCourse = navigateToCreateCourse,
                    navigateToCourseDetailsDisplay = navigateToCourseDetailsDisplay
                )
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CreateSemesterScreenPreview() {
//    CreateSemesterScreen()
}
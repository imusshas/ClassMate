package com.nasiat_muhib.classmate.presentation.main.create_semester

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.ErrorScreen
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseComponent
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseContent

@Composable
fun CreateSemesterScreen(
    navigateToTab: (TabItem) -> Unit,
    createSemesterViewModel: CreateSemesterViewModel = hiltViewModel()
) {

    val userState by createSemesterViewModel.userState.collectAsState()
    val courseState by createSemesterViewModel.courseState.collectAsState()
    val classDetailsListState by createSemesterViewModel.classDetailsState.collectAsState()

    var showWhat by rememberSaveable { mutableStateOf(true) }

    when (userState) {
        is ResponseState.Loading -> LoadingScreen()
        is ResponseState.Success -> userState.data?.let { user ->
            if (showWhat) {
                CreateCourseContent(
                    navigateToTab = navigateToTab,
                    onCreateCourseClick = { showWhat = false }
                )
            } else {
                CreateCourseComponent(
                    createSemesterViewModel = createSemesterViewModel,
                    user = user,
                    modifier = Modifier,
                    onCloseClick = { showWhat = true },
                    onCheckClick = { showWhat = true }
                )
            }
        }

        is ResponseState.Failure -> ErrorScreen(message = userState.error!!)
    }
}
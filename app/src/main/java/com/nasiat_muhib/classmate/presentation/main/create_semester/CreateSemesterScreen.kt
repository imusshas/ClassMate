package com.nasiat_muhib.classmate.presentation.main.create_semester

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.ClassMateAppScreen
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseComponent
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseContent

@Composable
fun CreateSemesterScreen(
    navigateToTab: (TabItem) -> Unit,
    createSemesterViewModel: CreateSemesterViewModel = hiltViewModel()
) {

    val uiState by createSemesterViewModel.userState.collectAsState()

    var showWhat by rememberSaveable { mutableStateOf(true) }

    when(uiState) {
        ResponseState.Loading -> LoadingScreen()
        is ResponseState.Success ->  {
            if (showWhat) {
                CreateCourseContent(navigateToTab = navigateToTab, onCreateCourseClick = {showWhat = false})
            } else {
                uiState.data?.let { user ->
                    CreateCourseComponent(
                        createSemesterViewModel = createSemesterViewModel,
                        user = user,
                        modifier = Modifier,
                        onCloseClick = {showWhat = true},
                        onCheckClick = {showWhat = true}
                    )
                }
            }
        }
        is ResponseState.Failure -> TODO()
    }
}
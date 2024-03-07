package com.nasiat_muhib.classmate.presentation.main.create_semester


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nasiat_muhib.classmate.components.ErrorScreen
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateSemesterContent
import com.nasiat_muhib.classmate.strings.TAG

@Composable
fun CreateSemesterScreen(
    createSemesterViewModel: CreateSemesterViewModel = viewModel()
) {
    val userState by createSemesterViewModel.userState.collectAsState()
    when (userState) {

        is DataState.Error -> ErrorScreen(error = userState.error.toString())

        DataState.Loading -> LoadingScreen()

        is DataState.Success ->  {
            userState.data?.let {
                Log.d(TAG, "CreateSemesterScreen: ${userState.data?.courses}")
                createSemesterViewModel.getCourses(it.courses)
                val courses by createSemesterViewModel.courses.collectAsState()
                CreateSemesterContent(
                    user = it,
                    createSemesterViewModel = createSemesterViewModel,
                )
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CreateSemesterScreenPreview() {
    CreateSemesterScreen()
}
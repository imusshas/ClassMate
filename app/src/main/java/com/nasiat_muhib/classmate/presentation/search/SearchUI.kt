package com.nasiat_muhib.classmate.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.domain.event.CreateCourseUIEvent
import com.nasiat_muhib.classmate.domain.event.CreateSemesterUIEvent
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterViewModel
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseViewModel
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MaximumHeight
import com.nasiat_muhib.classmate.ui.theme.NormalHeight

@Composable
fun SearchUI(
    searchViewModel: SearchViewModel = hiltViewModel(),
    createCourseViewModel: CreateCourseViewModel = hiltViewModel(),
) {
    val searchText = searchViewModel.searchText.collectAsState()
    val users = searchViewModel.users.collectAsState()
    val isSearching = searchViewModel.isSearching.collectAsState()

    val createCourseUIState = createCourseViewModel.createCourseUIState.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchText.value,
            onValueChange = searchViewModel::onSearch,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = MaximumHeight)
        ) {
            users.value.forEach {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(NormalHeight)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "${it.firstName} ${it.lastName}"
                            )
                            Spacer(modifier = Modifier.height(ExtraSmallSpace))
                            Text(
                                text = it.department
                            )
                        }

                        Button(
                            onClick = {
                                createCourseViewModel.onCreateCourse(
                                    CreateCourseUIEvent.SearchUISelectButtonClick(courseTeacherEmail = it.email)
                                )
                            }
                        ) {
                            Text(text = "Select")
                        }
                    }
                }
            }
        }
    }
}
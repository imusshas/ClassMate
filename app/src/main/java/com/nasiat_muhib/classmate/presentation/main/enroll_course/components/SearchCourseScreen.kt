package com.nasiat_muhib.classmate.presentation.main.enroll_course.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.SearchCourseUIEvent
import com.nasiat_muhib.classmate.domain.event.SearchUIEvent
import com.nasiat_muhib.classmate.strings.ENROLL_BUTTON
import com.nasiat_muhib.classmate.strings.SEARCH
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MediumRounded
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.TitleStyle

@Composable
fun SearchCourseScreen(
    searchViewModel: SearchCourseViewModel = hiltViewModel(),
    user: User
) {
    searchViewModel.getAllCourses(user)
    val searchText = searchViewModel.searchText.collectAsState()
    val courses = searchViewModel.courses.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchText.value,
            onValueChange = { searchText ->
                searchViewModel.onSearch(SearchUIEvent.SearchTextChanged(searchText))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = SEARCH) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            courses.value.forEach {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(NormalHeight)
                            .padding(horizontal = SmallSpace)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = it.courseCode,
                                style = TitleStyle
                            )
                            Spacer(modifier = Modifier.height(ExtraSmallSpace))
                            Text(
                                text = it.courseTitle,
                            )
                            Spacer(modifier = Modifier.height(ExtraSmallSpace))
                            Text(
                                text = it.courseDepartment,
                            )
                        }

                        Button(
                            onClick = {
                                val courseId = "${it.courseDepartment}:${it.courseCode}"
                                searchViewModel.onSearchCourseEvent(
                                    SearchCourseUIEvent.EnrollButtonClicked(
                                        courseId,
                                        user.email
                                    )
                                )
                            },
                            shape = MediumRounded
                        ) {
                            Text(text = ENROLL_BUTTON)
                        }
                    }
                }
            }
        }
    }
}
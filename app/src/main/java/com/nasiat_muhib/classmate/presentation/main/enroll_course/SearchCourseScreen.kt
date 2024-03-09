package com.nasiat_muhib.classmate.presentation.main.enroll_course

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
import com.nasiat_muhib.classmate.domain.event.EnrollCourseUIEvent
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseViewModel
import com.nasiat_muhib.classmate.strings.REQUEST_BUTTON
import com.nasiat_muhib.classmate.strings.SEARCH
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MediumRounded
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.TitleStyle

@Composable
fun SearchTeacherScreen(
    searchViewModel: SearchCourseViewModel = hiltViewModel(),
) {
    val searchText = searchViewModel.searchText.collectAsState()
    val users = searchViewModel.users.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchText.value,
            onValueChange = searchViewModel::onSearch,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = SEARCH) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            users.value.forEach {
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
                                      /*TODO*/
                            },
                            shape = MediumRounded
                        ) {
                            Text(text = REQUEST_BUTTON)
                        }
                    }
                }
            }
        }
    }
}
package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.domain.event.CreateCourseUIEvent
import com.nasiat_muhib.classmate.strings.CREATE_COURSE
import com.nasiat_muhib.classmate.strings.CREATE_ICON
import com.nasiat_muhib.classmate.strings.GO_BACK_ICON
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.TitleStyle

@Composable
fun CreateCourseTopBar(
    createCourseViewModel: CreateCourseViewModel,
    navigateBackToCreateSemester: () -> Unit
) {
    val navigationState = createCourseViewModel.navigationState.collectAsState().value

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = GO_BACK_ICON,
            modifier = Modifier
                .clickable {
                    createCourseViewModel.onCreateCourse(CreateCourseUIEvent.BackButtonClick)
                    navigateBackToCreateSemester()
                }
        )
        Text(text = CREATE_COURSE, style = TitleStyle)

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = CREATE_ICON,
            modifier = Modifier.clickable {
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CreateClick)
                if (navigationState) {
                    navigateBackToCreateSemester()
                }
            }
        )
    }
}
package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.event.CreateCourseUIEvent
import com.nasiat_muhib.classmate.strings.COURSE_CODE_LABEL
import com.nasiat_muhib.classmate.strings.COURSE_CREDIT_LABEL
import com.nasiat_muhib.classmate.strings.COURSE_TEACHER_EMAIL_LABEL
import com.nasiat_muhib.classmate.strings.COURSE_TITLE_LABEL
import com.nasiat_muhib.classmate.strings.CREATE_CLASS_BUTTON
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateCourse(
    createCourseViewModel: CreateCourseViewModel = viewModel()
) {

    val createCourseUIState by createCourseViewModel.createCourseUIState.collectAsState()
    val classDetailsList by createCourseViewModel.classDetailsDataList.collectAsState()

    val localFocusManager = LocalFocusManager.current
    val createClassDialogState by createCourseViewModel.createCourseDialogState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CreateCourseTopBar(createCourseViewModel)

        CustomOutlinedField(
            labelValue = COURSE_CODE_LABEL,
            onValueChange = { courseCode ->
                createCourseViewModel.onCreateCourse(
                    CreateCourseUIEvent.CourseCodeChanged(
                        courseCode
                    )
                )
            },
            errorMessage = createCourseUIState.courseCodeError
        )
        CustomOutlinedField(
            labelValue = COURSE_TITLE_LABEL,
            onValueChange = {courseTitle ->
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CourseTitleChanged(courseTitle))},
            errorMessage = createCourseUIState.courseTitleError
        )
        CustomOutlinedField(
            labelValue = COURSE_CREDIT_LABEL,
            onValueChange = { courseCredit ->
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CourseCreditChanged(courseCredit))
            },
            errorMessage = createCourseUIState.courseCreditError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        CustomOutlinedField(
            labelValue = COURSE_TEACHER_EMAIL_LABEL,
            onValueChange = {courseTeacherEmail ->
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CourseTeacherEmailChanged(courseTeacherEmail))},
            errorMessage = createCourseUIState.courseTeacherEmailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                localFocusManager.clearFocus()
            }
        )

        Spacer(modifier = Modifier.height(ExtraExtraLargeSpace))

        CustomElevatedButton(
            text = CREATE_CLASS_BUTTON,
            onClick = {
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CreateClassButtonClick)
            }
        )

        if (createClassDialogState) {
            CreateClass(createCourseViewModel)
        }

        Spacer(modifier = Modifier.height(LargeSpace))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(SmallSpace),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClassDetailsTitle()
            classDetailsList.forEach {details ->
                EditClassDetails(classDetails = details)
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateCoursePreview() {
    CreateCourse()
}
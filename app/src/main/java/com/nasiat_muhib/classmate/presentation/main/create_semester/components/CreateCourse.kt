package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.nasiat_muhib.classmate.components.CustomDropDownMenu
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.core.Constants.SEMESTERS
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.event.CreateCourseUIEvent
import com.nasiat_muhib.classmate.domain.event.SignUpUIEvent
import com.nasiat_muhib.classmate.strings.COURSE_CODE_LABEL
import com.nasiat_muhib.classmate.strings.COURSE_CREDIT_LABEL
import com.nasiat_muhib.classmate.strings.COURSE_DEPARTMENT_LABEL
import com.nasiat_muhib.classmate.strings.COURSE_TEACHER_EMAIL_LABEL
import com.nasiat_muhib.classmate.strings.COURSE_TITLE_LABEL
import com.nasiat_muhib.classmate.strings.CREATE_CLASS_BUTTON
import com.nasiat_muhib.classmate.strings.ROLE_HARDCODED
import com.nasiat_muhib.classmate.strings.SEMESTER_HARD_CODED
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace

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
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomOutlinedField(
                labelValue = COURSE_DEPARTMENT_LABEL,
                onValueChange = { courseDepartment ->
                                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CourseDepartmentChanged(courseDepartment))
                },
                errorMessage = createCourseUIState.courseDepartmentError,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = ZeroSpace)
            )
            CustomOutlinedField(
                labelValue = COURSE_CREDIT_LABEL,
                onValueChange = { courseCredit ->
                    createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CourseCreditChanged(courseCredit))
                },
                errorMessage = createCourseUIState.courseCreditError,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = ZeroSpace),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = SEMESTER_HARD_CODED, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MediumSpace)
            )
            CustomDropDownMenu(
                itemList = SEMESTERS,
                onItemChange = { semester ->
//                    signUpViewModel.onEvent(SignUpUIEvent.RoleChanged(role))
                }
            )
        }
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
package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.nasiat_muhib.classmate.components.TitleContainer
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
import com.nasiat_muhib.classmate.strings.CREATED_CLASSES
import com.nasiat_muhib.classmate.strings.CREATE_CLASS_BUTTON
import com.nasiat_muhib.classmate.strings.ROLE_HARDCODED
import com.nasiat_muhib.classmate.strings.SEARCH_COURSE_TEACHER_BUTTON
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

    val createClassDialogState by createCourseViewModel.createCourseDialogState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CreateCourseTopBar(createCourseViewModel)

        CustomOutlinedField(
            value = createCourseUIState.courseCode,
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
            value = createCourseUIState.courseTitle,
            labelValue = COURSE_TITLE_LABEL,
            onValueChange = {courseTitle ->
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CourseTitleChanged(courseTitle))},
            errorMessage = createCourseUIState.courseTitleError
        )
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomOutlinedField(
                value = createCourseUIState.courseCredit.toString(),
                labelValue = COURSE_CREDIT_LABEL,
                onValueChange = { courseCredit ->
                    createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CourseCreditChanged(courseCredit))
                },
                errorMessage = createCourseUIState.courseCreditError,
                modifier = Modifier
                    .weight(1f),
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
                    createCourseViewModel.onCreateCourse(CreateCourseUIEvent.CourseSemesterChanged(semester))
                }
            )
        }

        Spacer(modifier = Modifier.height(MediumSpace))

        // Search Teacher Button
        CustomElevatedButton(
            text = if (createCourseUIState.courseTeacherEmailError == null) SEARCH_COURSE_TEACHER_BUTTON else createCourseUIState.courseTeacherEmailError!!,
            onClick = {
                createCourseViewModel.onCreateCourse(CreateCourseUIEvent.SearchTeacherButtonClick)
            },
            contentColor = if (createCourseUIState.courseTeacherEmailError == null) ButtonDefaults.elevatedButtonColors().contentColor else MaterialTheme.colorScheme.error,
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
            TitleContainer(title = CREATED_CLASSES)
            if (createCourseUIState.createClassError != null) {
                Text(
                    text = createCourseUIState.createClassError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
//            LazyColumn(
//                modifier = Modifier.fillMaxWidth().padding(horizontal = MediumSpace)
//            ) {
//                items(classDetailsList.size) {index ->
//
//                }
//            }
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
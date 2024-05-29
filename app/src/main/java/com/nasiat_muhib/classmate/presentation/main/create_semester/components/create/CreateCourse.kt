package com.nasiat_muhib.classmate.presentation.main.create_semester.components.create

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.nasiat_muhib.classmate.components.CustomDropDownMenu
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomLazyColumn
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.CustomSwipeAbleLazyColumn
import com.nasiat_muhib.classmate.components.TitleContainer
import com.nasiat_muhib.classmate.core.Constants.SEMESTERS
import com.nasiat_muhib.classmate.domain.event.CreateCourseUIEvent
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseTopBar
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseViewModel
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.DisplayClassDetails
import com.nasiat_muhib.classmate.strings.COURSE_CODE_LABEL
import com.nasiat_muhib.classmate.strings.COURSE_CREDIT_LABEL
import com.nasiat_muhib.classmate.strings.COURSE_TITLE_LABEL
import com.nasiat_muhib.classmate.strings.CREATED_CLASSES_TITLE
import com.nasiat_muhib.classmate.strings.CREATE_CLASS_BUTTON
import com.nasiat_muhib.classmate.strings.SEARCH_COURSE_TEACHER_BUTTON
import com.nasiat_muhib.classmate.strings.SEMESTER_HARD_CODED
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateCourse(
    createCourseViewModel: CreateCourseViewModel,
    navigateToSearchTeacher: () -> Unit,
    navigateBackToCreateSemester: () -> Unit
) {

    createCourseViewModel.getUser()
    val createCourseUIState = createCourseViewModel.createCourseUIState.collectAsState().value
    val createClassDialogState = createCourseViewModel.createCourseDialogState.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CreateCourseTopBar(
            createCourseViewModel,
            navigateBackToCreateSemester
        )

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
            onValueChange = { courseTitle ->
                createCourseViewModel.onCreateCourse(
                    CreateCourseUIEvent.CourseTitleChanged(
                        courseTitle
                    )
                )
            },
            errorMessage = createCourseUIState.courseTitleError
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomOutlinedField(
                value = createCourseUIState.courseCredit.toString(),
                labelValue = COURSE_CREDIT_LABEL,
                onValueChange = { courseCredit ->
                    createCourseViewModel.onCreateCourse(
                        CreateCourseUIEvent.CourseCreditChanged(
                            courseCredit
                        )
                    )
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
                    createCourseViewModel.onCreateCourse(
                        CreateCourseUIEvent.CourseSemesterChanged(
                            semester
                        )
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(MediumSpace))

        // Search Teacher Button
        CustomElevatedButton(
            text = if (createCourseUIState.courseTeacherEmailError == null) SEARCH_COURSE_TEACHER_BUTTON else createCourseUIState.courseTeacherEmailError!!,
            onClick = navigateToSearchTeacher,
            contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
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

            TitleContainer(title = CREATED_CLASSES_TITLE)

            if (createCourseUIState.createClassError != null) {
                Text(
                    text = createCourseUIState.createClassError,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }


            CustomSwipeAbleLazyColumn(
                items = createCourseUIState.courseClasses.toList(),
                key = {
                    "${it.hashCode()}"
                }
            ) {
                DisplayClassDetails(classDetails = it, createCourseViewModel = createCourseViewModel)
            }
        }
    }
}
package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.strings.COURSE_CODE
import com.nasiat_muhib.classmate.strings.COURSE_CREDIT
import com.nasiat_muhib.classmate.strings.COURSE_TEACHER_EMAIL
import com.nasiat_muhib.classmate.strings.COURSE_TITLE
import com.nasiat_muhib.classmate.strings.CREATE_CLASS
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraLargeSpace

@Composable
fun CreateCourse(
    createSemesterViewModel: CreateSemesterViewModel = viewModel()
) {

    val localFocusManager = LocalFocusManager.current
    val createClassState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CreateCourseTopBar()

        CustomOutlinedField(labelValue = COURSE_CODE, onValueChange = {})
        CustomOutlinedField(labelValue = COURSE_TITLE, onValueChange = {})
        CustomOutlinedField(
            labelValue = COURSE_CREDIT,
            onValueChange = {},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        CustomOutlinedField(
            labelValue = COURSE_TEACHER_EMAIL,
            onValueChange = {},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions {
                localFocusManager.clearFocus()
            }
        )

        Spacer(modifier = Modifier.height(ExtraExtraLargeSpace))

        CustomElevatedButton(text = CREATE_CLASS, onClick = {
            createClassState.value = true
        })

        if (createClassState.value) {
            CreateClass()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateCoursePreview() {
    CreateCourse()
}
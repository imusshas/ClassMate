package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.strings.COURSE_CODE
import com.nasiat_muhib.classmate.strings.COURSE_CREDIT
import com.nasiat_muhib.classmate.strings.COURSE_TEACHER_EMAIL
import com.nasiat_muhib.classmate.strings.COURSE_TITLE
import com.nasiat_muhib.classmate.strings.CREATE_CLASS
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.ExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateCourse() {

    val localFocusManager = LocalFocusManager.current

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CreateCourseTopBar()

        CustomOutlinedField(labelValue = COURSE_CODE, onValueChange = {})
        CustomOutlinedField(labelValue = COURSE_TITLE, onValueChange = {})
        CustomOutlinedField(labelValue = COURSE_CREDIT, onValueChange = {})
        CustomOutlinedField(
            labelValue = COURSE_TEACHER_EMAIL,
            onValueChange = {},
            keyboardActions = KeyboardActions {
                localFocusManager.clearFocus()
            }
        )

        Spacer(modifier = Modifier.height(ExtraExtraLargeSpace))

        CustomElevatedButton(text = CREATE_CLASS, onClick = { /*TODO*/ })
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateCoursePreview() {
    CreateCourse()
}
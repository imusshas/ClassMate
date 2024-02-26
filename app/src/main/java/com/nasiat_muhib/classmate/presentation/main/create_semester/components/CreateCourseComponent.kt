package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.NormalField
import com.nasiat_muhib.classmate.components.PasswordField

@Composable
fun CreateCourseComponent(
    showWhat: Boolean,
    showSheet: Boolean,
    code: String,
    title: String,
    teacher: String,
    password: String,
    classroom: String,
    onCreateCourseClick: () -> Unit,
    onCodeChange: (String) -> Unit,
    onTitleChange: (String) -> Unit,
    onTeacherChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClassroomChange: (String) -> Unit,
    onCreateClassClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier
) {



    if (showWhat) {
        Button(onClick = onCreateCourseClick) {
            Text(text = "Create Course")
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NormalField(value = code, onValueChange = onCodeChange, label = "Course Code", imeAction = ImeAction.Next)
            NormalField(value = title, onValueChange = onTitleChange, label = "Course Title", imeAction = ImeAction.Next)
            NormalField(value = teacher, onValueChange = onTeacherChange, label = "Course Teacher Email", imeAction = ImeAction.Next)
            PasswordField(password = password, onPasswordChange = onPasswordChange, label = "Course Password", imeAction = ImeAction.Done)
        }

        Button(onClick = onCreateClassClick) {
            Text(text = "Create Class")
        }

        OutlinedButton(onClick = onDoneClick) {
            Text(text = "Done")
        }
    }

    if(showSheet) {
        CreateClassComponent(
            classroom = classroom,
            onClassroomChange = onClassroomChange,
            onDismissRequest = onDismissRequest,
            onCancelClick = onDismissRequest,
            onConfirmClick = { /*TODO*/ }
        )
    }
}
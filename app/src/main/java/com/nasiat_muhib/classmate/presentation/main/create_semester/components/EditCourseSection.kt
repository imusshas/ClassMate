package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.NormalField
import com.nasiat_muhib.classmate.core.Constants.COURSE_CODE_LABEL
import com.nasiat_muhib.classmate.core.Constants.COURSE_CREDIT_LABEL
import com.nasiat_muhib.classmate.core.Constants.COURSE_TEACHER_LABEL
import com.nasiat_muhib.classmate.core.Constants.COURSE_TITLE_LABEL

@Composable
fun EditCourseSection(
    code: String,
    onCodeChange: (String) -> Unit,
    title: String,
    onTitleChange: (String) -> Unit,
    teacher: String,
    onTeacherChange: (String) -> Unit,
    credit: String,
    onCreditChange: (String) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NormalField(
            value = code,
            onValueChange = onCodeChange,
            readOnly = enabled,
            label = COURSE_CODE_LABEL,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth()
        )
        NormalField(
            value = title,
            onValueChange = onTitleChange,
            readOnly = enabled,
            label = COURSE_TITLE_LABEL,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth()
        )
        NormalField(
            value = credit,
            onValueChange = onCreditChange,
            readOnly = enabled,
            label = COURSE_CREDIT_LABEL,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth()
        )
        NormalField(
            value = teacher,
            onValueChange = onTeacherChange,
            readOnly = enabled,
            label = COURSE_TEACHER_LABEL,
            imeAction = ImeAction.Done,
            modifier = Modifier.fillMaxWidth()
        )
    }

}
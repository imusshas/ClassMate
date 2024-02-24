package com.nasiat_muhib.classmate.presentation.main.enroll_course.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.NormalField
import com.nasiat_muhib.classmate.core.Constants.COURSE_CODE_LABEL
import com.nasiat_muhib.classmate.core.Constants.COURSE_PASSWORD_LABEL
import com.nasiat_muhib.classmate.core.Constants.ENROLL_BUTTON

@Composable
fun EnrollCourseContent() {

    var code by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NormalField(value = code, onValueChange = {code = it}, label = COURSE_CODE_LABEL, imeAction = ImeAction.Next)
            NormalField(value = password, onValueChange = {password = it}, label = COURSE_PASSWORD_LABEL, imeAction = ImeAction.Done)
        }
        
        Button(onClick = { /*TODO*/ }) {
            Text(text = ENROLL_BUTTON)
        }
    }
}
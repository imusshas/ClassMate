package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nasiat_muhib.classmate.components.CustomDialog
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.CustomTimePicker
import com.nasiat_muhib.classmate.ui.theme.ExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateClass() {
    CustomDialog(
        onDismissRequest = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = ExtraLargeSpace)
            ,
            verticalArrangement = Arrangement.spacedBy(SmallSpace)
        ) {
            CustomOutlinedField(labelValue = "Classroom", onValueChange = {})
            CustomOutlinedField(labelValue = "Section", onValueChange = {})
            CustomTimePicker(
                onHourChange = {},
                onMinuteChange = {},
                onShiftClick = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CreateClassPreview() {
    CreateClass()
}
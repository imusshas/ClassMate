package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nasiat_muhib.classmate.components.CustomDialog
import com.nasiat_muhib.classmate.components.NormalField
import com.nasiat_muhib.classmate.components.dayTimePicker
import com.nasiat_muhib.classmate.core.Constants.CANCEL_BUTTON
import com.nasiat_muhib.classmate.core.Constants.CONFIRM_BUTTON

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateClassComponent(
    classroom: String,
    onClassroomChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    var sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        ModalBottomSheetContent(
            classroom = classroom,
            onClassroomChange = onClassroomChange,
            onCancelClick = onCancelClick,
            onConfirmClick = onConfirmClick,
            modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)
        )
    }
}

@Composable
private fun ModalBottomSheetContent(
    classroom: String,
    onClassroomChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        dayTimePicker()
        NormalField(
            value = classroom,
            onValueChange = onClassroomChange,
            label = "Classroom",
            imeAction = ImeAction.Done,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = onCancelClick) {
                Text(text = CANCEL_BUTTON)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onConfirmClick) {
                Text(text = CONFIRM_BUTTON)
            }
        }
    }
}
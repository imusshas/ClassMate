package com.nasiat_muhib.classmate.presentation.main.create_semester.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.AutoCompleteField
import com.nasiat_muhib.classmate.components.CustomDialog
import com.nasiat_muhib.classmate.components.NormalField
import com.nasiat_muhib.classmate.components.TimePickerField
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.core.Constants.CANCEL_BUTTON
import com.nasiat_muhib.classmate.core.Constants.CLASSROOM_LABEL
import com.nasiat_muhib.classmate.core.Constants.OK_BUTTON
import com.nasiat_muhib.classmate.core.Constants.WEEKDAY_LABEL
import com.nasiat_muhib.classmate.core.UtilityVariables
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.ui.theme.ButtonBoldStyle

@Composable
fun CreateClassSection(
    onDismissRequest: () -> Unit,
    classroom: String,
    onClassroomChange: (String) -> Unit,
    weekDay: String,
    onWeekDayChange: (String) -> Unit,
    hour: String,
    onHourChange: (String) -> Unit,
    minute: String,
    onMinuteChange: (String) -> Unit,
    dayNight: Boolean,
    onAmClick: () -> Unit,
    onPmClick: () -> Unit,
    onOkClick: () -> Unit,
    onCancelClick: () -> Unit,
) {

    CustomDialog(
        onDismissRequest = onDismissRequest,
        fraction = 0.95f
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NormalField(
                value = classroom,
                onValueChange = onClassroomChange,
                label = CLASSROOM_LABEL,
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )
            AutoCompleteField(
                itemsList = UtilityVariables.DAYS_OF_WEEK,
                selectedItem = weekDay,
                onItemChange = onWeekDayChange,
                label = WEEKDAY_LABEL,
                imeAction = ImeAction.Done
            )

            TimePickerField(
                hour = hour,
                minute = minute,
                dayNight = dayNight,
                onHourChange = onHourChange,
                onMinuteChange = onMinuteChange,
                onAmClick = onAmClick,
                onPmClick = onPmClick,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = CANCEL_BUTTON, modifier = Modifier
                    .padding(4.dp)
                    .clickable { onCancelClick.invoke() },
                    style = ButtonBoldStyle,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = OK_BUTTON, modifier = Modifier
                    .padding(4.dp)
                    .clickable {
                        onOkClick.invoke()
                    },
                    style = ButtonBoldStyle,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
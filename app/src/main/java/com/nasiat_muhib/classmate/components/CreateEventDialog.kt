package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.nasiat_muhib.classmate.core.Constants.EVENTS
import com.nasiat_muhib.classmate.strings.CANCEL_BUTTON
import com.nasiat_muhib.classmate.strings.CLASSROOM_LABEL
import com.nasiat_muhib.classmate.strings.CREATE_BUTTON
import com.nasiat_muhib.classmate.ui.theme.ClickableTextStyle
import com.nasiat_muhib.classmate.ui.theme.ExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateEventDialog (
    onEventChange: (String) -> Unit,
    onClassroomChange: (String) -> Unit,
    onDayChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    onShiftChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onCreateClick: () -> Unit,
    classroomError: String? = null,
    timeOrDateError: String? = null,
) {

    CustomDialog(
        onDismissRequest = {
            onCancelClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = ExtraLargeSpace),
            verticalArrangement = Arrangement.spacedBy(SmallSpace)
        ) {

            // Event Type
            CustomDropDownMenu(
                itemList = EVENTS,
                onItemChange = {type ->
                    onEventChange(type)
                }
            )

            // Classroom
            CustomOutlinedField(
                labelValue = CLASSROOM_LABEL,
                onValueChange = { classroom ->
                    onClassroomChange(classroom)
                }, errorMessage = classroomError
            )


            // Date Picker
            CustomDatePicker(
                onDayChange = { day ->
                    onDayChange(day)
                },
                onMonthChange = {month ->
                                onMonthChange(month)
                },
                onYearChange = {year ->
                    onYearChange(year)
                }
            )


            // Time Picker
            CustomTimePicker(

                onHourChange = { hour ->
                    onHourChange(hour)
                },
                onMinuteChange = { minute ->
                    onMinuteChange(minute)
                },
                onShiftClick = { shift ->
                    onShiftChange(shift)
                }
            )

            if (timeOrDateError != null) {
                Spacer(modifier = Modifier.height(ExtraSmallSpace))
                Text(
                    text = timeOrDateError,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = MediumSpace)
                )
            }

            Spacer(modifier = Modifier.height(ExtraLargeSpace))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumSpace),
                horizontalArrangement = Arrangement.End
            ) {
                CustomClickableText(
                    text = CANCEL_BUTTON,
                    onClick = {
                        onCancelClick()
                    },
                    style = ClickableTextStyle.copy(
                        textDecoration = TextDecoration.None,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(SmallSpace))
                CustomClickableText(
                    text = CREATE_BUTTON,
                    onClick = {
                        onCreateClick()
                    },
                    style = ClickableTextStyle.copy(
                        textDecoration = TextDecoration.None,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CreateClassPreview() {
//    CreateClass(hiltViewModel())
}
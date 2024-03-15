package com.nasiat_muhib.classmate.presentation.main.components.display_course

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.nasiat_muhib.classmate.components.CustomClickableText
import com.nasiat_muhib.classmate.components.CustomDatePicker
import com.nasiat_muhib.classmate.components.CustomDialog
import com.nasiat_muhib.classmate.components.CustomDropDownMenu
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.CustomTimePicker
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.domain.event.CreateClassUIEvent
import com.nasiat_muhib.classmate.domain.event.CreateTermTestUIEvent
import com.nasiat_muhib.classmate.strings.CANCEL_BUTTON
import com.nasiat_muhib.classmate.strings.CLASSROOM_LABEL
import com.nasiat_muhib.classmate.strings.CREATE_BUTTON
import com.nasiat_muhib.classmate.strings.END_TIME
import com.nasiat_muhib.classmate.strings.SECTION_LABEL
import com.nasiat_muhib.classmate.strings.START_TIME
import com.nasiat_muhib.classmate.ui.theme.ClickableTextStyle
import com.nasiat_muhib.classmate.ui.theme.ExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateTermTest(
    courseDetailsDisplayViewModel: CourseDetailsDisplayViewModel,
) {

    val creteTermTestUIState by courseDetailsDisplayViewModel.termTestUIState.collectAsState()

    CustomDialog(
        onDismissRequest = {
            courseDetailsDisplayViewModel.onCreateTermTestEvent(CreateTermTestUIEvent.TermTestCancelButtonClick)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = ExtraLargeSpace),
            verticalArrangement = Arrangement.spacedBy(SmallSpace)
        ) {

            // Classroom
            CustomOutlinedField(
                labelValue = CLASSROOM_LABEL,
                onValueChange = { classroom ->
                    courseDetailsDisplayViewModel.onCreateTermTestEvent(
                        CreateTermTestUIEvent.TermTestClassroomChanged(
                            classroom
                        )
                    )
                }, errorMessage = creteTermTestUIState.classroomError
            )

            // Date Picker
            CustomDatePicker(
                onDayChange = { day ->
                              courseDetailsDisplayViewModel.onCreateTermTestEvent(
                                  CreateTermTestUIEvent.TermTestDayChanged(day)
                              )
                },
                onMonthChange = { month ->
                                courseDetailsDisplayViewModel.onCreateTermTestEvent(
                                    CreateTermTestUIEvent.TermTestMonthChanged(month)
                                )
                },
                onYearChange = { year ->
                    courseDetailsDisplayViewModel.onCreateTermTestEvent(
                        CreateTermTestUIEvent.TermTestYearChanged(year)
                    )

                }
            )

            // Time Picker
            CustomTimePicker(
                onHourChange = { hour ->
                    courseDetailsDisplayViewModel.onCreateTermTestEvent(
                        CreateTermTestUIEvent.TermTestHourChanged(hour)
                    )
                },
                onMinuteChange = { minute ->
                    courseDetailsDisplayViewModel.onCreateTermTestEvent(
                        CreateTermTestUIEvent.TermTestMinuteChanged(minute)
                    )
                },
                onShiftClick = { shift ->
                    courseDetailsDisplayViewModel.onCreateTermTestEvent(
                        CreateTermTestUIEvent.TermTestShiftChanged(shift)
                    )
                }
            )

            if (creteTermTestUIState.dateError != null) {
                Spacer(modifier = Modifier.height(ExtraSmallSpace))
                Text(
                    text = creteTermTestUIState.dateError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = MediumSpace)
                )
            } else if (creteTermTestUIState.timeError != null) {
                Spacer(modifier = Modifier.height(ExtraSmallSpace))
                Text(
                    text = creteTermTestUIState.timeError!!,
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
                        courseDetailsDisplayViewModel.onCreateTermTestEvent(CreateTermTestUIEvent.TermTestCancelButtonClick)
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
                        courseDetailsDisplayViewModel.onCreateTermTestEvent(CreateTermTestUIEvent.TermTestCreateButtonClick)
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
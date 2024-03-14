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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.CustomClickableText
import com.nasiat_muhib.classmate.components.CustomDialog
import com.nasiat_muhib.classmate.components.CustomDropDownMenu
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.CustomTimePicker
import com.nasiat_muhib.classmate.core.Constants.WEEK_DAYS
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.event.CreateClassUIEvent
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseViewModel
import com.nasiat_muhib.classmate.strings.CANCEL_BUTTON
import com.nasiat_muhib.classmate.strings.CLASSROOM_LABEL
import com.nasiat_muhib.classmate.strings.CREATE_BUTTON
import com.nasiat_muhib.classmate.strings.EDIT_BUTTON
import com.nasiat_muhib.classmate.strings.END_TIME
import com.nasiat_muhib.classmate.strings.SECTION_LABEL
import com.nasiat_muhib.classmate.strings.START_TIME
import com.nasiat_muhib.classmate.ui.theme.ClickableTextStyle
import com.nasiat_muhib.classmate.ui.theme.ExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CreateClassOnDisplay(
    courseDetailsDisplayViewModel: CourseDetailsDisplayViewModel,
) {

    val createClassUIState by courseDetailsDisplayViewModel.createClassUIState.collectAsState()
    CustomDialog(
        onDismissRequest = {
            courseDetailsDisplayViewModel.onCreateClassEvent(CreateClassUIEvent.CancelButtonClick)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = ExtraLargeSpace),
            verticalArrangement = Arrangement.spacedBy(SmallSpace)
        ) {

            // WeekDay Picker
            CustomDropDownMenu(
                itemList = WEEK_DAYS,
                selectedItem =  WEEK_DAYS[0] ,
                onItemChange = { weekDay ->
                    courseDetailsDisplayViewModel.onCreateClassEvent(CreateClassUIEvent.WeekDayChanged(weekDay))
                }
            )

            // Classroom
            CustomOutlinedField(
                labelValue = CLASSROOM_LABEL,
                onValueChange = { classroom ->
                    courseDetailsDisplayViewModel.onCreateClassEvent(
                        CreateClassUIEvent.ClassRoomChanged(
                            classroom
                        )
                    )
                }, errorMessage = createClassUIState.classroomError
            )

            // Section
            CustomOutlinedField(
                labelValue = SECTION_LABEL,
                onValueChange = { section ->
                    courseDetailsDisplayViewModel.onCreateClassEvent(CreateClassUIEvent.SectionChanged(section))

                },
                errorMessage = createClassUIState.sectionError
            )

            // Start Time Picker
            CustomTimePicker(
                title = START_TIME,
                onHourChange = { startHour ->
                    courseDetailsDisplayViewModel.onCreateClassEvent(
                        CreateClassUIEvent.StartHourChanged(startHour)
                    )
                },
                onMinuteChange = { startMinute ->
                    courseDetailsDisplayViewModel.onCreateClassEvent(
                        CreateClassUIEvent.StartMinuteChanged(startMinute)
                    )
                },
                onShiftClick = { startShift ->
                    courseDetailsDisplayViewModel.onCreateClassEvent(
                        CreateClassUIEvent.StartShiftChanged(startShift)
                    )
                }
            )

            // End Time Picker
            CustomTimePicker(
                title = END_TIME,
                onHourChange = { endHour ->
                    courseDetailsDisplayViewModel.onCreateClassEvent(CreateClassUIEvent.EndHourChanged(endHour))
                },
                onMinuteChange = { endMinute ->
                    courseDetailsDisplayViewModel.onCreateClassEvent(
                        CreateClassUIEvent.EndMinuteChanged(endMinute)
                    )
                },
                onShiftClick = { endShift ->
                    courseDetailsDisplayViewModel.onCreateClassEvent(CreateClassUIEvent.EndShiftChanged(endShift))
                }
            )
            if (createClassUIState.timeError != null) {
                Spacer(modifier = Modifier.height(ExtraSmallSpace))
                Text(
                    text = createClassUIState.timeError!!,
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
                        courseDetailsDisplayViewModel.onCreateClassEvent(CreateClassUIEvent.CancelButtonClick)
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
                        courseDetailsDisplayViewModel.onCreateClassEvent(CreateClassUIEvent.CreateButtonClick)
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
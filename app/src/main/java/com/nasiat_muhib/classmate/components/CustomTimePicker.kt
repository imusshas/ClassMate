package com.nasiat_muhib.classmate.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.nasiat_muhib.classmate.strings.AM
import com.nasiat_muhib.classmate.strings.COLON
import com.nasiat_muhib.classmate.strings.PM
import com.nasiat_muhib.classmate.strings.TIME
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.LargeHeight
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.PickerStyle
import com.nasiat_muhib.classmate.ui.theme.SmallBorder
import com.nasiat_muhib.classmate.ui.theme.SmallHeight
import com.nasiat_muhib.classmate.ui.theme.SmallPickerStyle
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace
import java.time.LocalTime

@Composable
fun CustomTimePicker(
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    onShiftClick: (String) -> Unit,
    title: String = TIME,
    topPadding: Dp = ZeroSpace,
    bottomPadding: Dp = ZeroSpace,
    startPadding: Dp = MediumSpace,
    endPadding: Dp = MediumSpace,
) {

    val localFocusManager = LocalFocusManager.current

    val time = LocalTime.now()
    val currentHour = if (time.hour > 12) time.hour - 12 else if (time.hour == 0) 12 else time.hour
    val hour = rememberSaveable { mutableStateOf( currentHour.toString()  ) }
    val minute = rememberSaveable { mutableStateOf( time.minute.toString() ) }

    val hourClicked = rememberSaveable { mutableStateOf(false) }
    val minuteClicked = rememberSaveable { mutableStateOf(false) }
    val shiftClicked = rememberSaveable { mutableStateOf(false) }
    val isAmSelected = rememberSaveable { mutableStateOf( time.hour < 12) }

    if(!hourClicked.value) {
        onHourChange(hour.value)
    }
    if(!minuteClicked.value) {
        onMinuteChange(minute.value)
    }
    if(!shiftClicked.value) {
        onShiftClick(if (isAmSelected.value) AM else PM)
    }


    val amContainerColor =
        if (isAmSelected.value) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f) else MaterialTheme.colorScheme.background
    val amContentColor =
        if (isAmSelected.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    val pmContainerColor =
        if (!isAmSelected.value) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f) else MaterialTheme.colorScheme.background
    val pmContentColor =
        if (!isAmSelected.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = topPadding,
                start = startPadding,
                end = endPadding,
                bottom = bottomPadding
            )
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontSize = SmallPickerStyle.fontSize
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(ExtraSmallSpace),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomBasicTextField(
                    value = hour.value,
                    onValueChange = {
                    onHourChange(it)
                    hourClicked.value = true
//                    Log.d(TAG, "CustomTimePicker: hour $it")
                })
                Text(
                    text = COLON,
                    style = PickerStyle,
                    color = MaterialTheme.colorScheme.primary
                )
                CustomBasicTextField(
                    value = minute.value,
                    onValueChange = {
                        onMinuteChange(it)
                        minuteClicked.value = true
                    },
                    keyboardActions = KeyboardActions {
                        localFocusManager.clearFocus()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }

            Spacer(modifier = Modifier.width(MediumSpace))
            Column(
                modifier = Modifier.height(LargeHeight),
                verticalArrangement = Arrangement.spacedBy(ExtraSmallSpace)
            ) {
                Box(
                    modifier = Modifier
                        .clip(LargeRounded)
                        .size(SmallHeight)
                        .background(amContainerColor)
                        .border(SmallBorder, amContentColor, LargeRounded)
                        .clickable {
                            localFocusManager.clearFocus()
                            isAmSelected.value = true
                            shiftClicked.value = true
                            onShiftClick(AM)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = AM, style = SmallPickerStyle, color = amContentColor)
                }

                Box(
                    modifier = Modifier
                        .clip(LargeRounded)
                        .size(SmallHeight)
                        .border(SmallBorder, pmContentColor, LargeRounded)
                        .background(pmContainerColor)
                        .clickable {
                            localFocusManager.clearFocus()
                            isAmSelected.value = false
                            shiftClicked.value = true
                            onShiftClick(PM)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = PM, style = SmallPickerStyle, color = pmContentColor)
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TimePickerPreview() {
    CustomTimePicker(
        onHourChange = {},
        onMinuteChange = {},
        onShiftClick = {}
    )
}
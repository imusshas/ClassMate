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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.strings.AM
import com.nasiat_muhib.classmate.strings.COLON
import com.nasiat_muhib.classmate.strings.PM
import com.nasiat_muhib.classmate.strings.TIME
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.LargeHeight
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.PickerStyle
import com.nasiat_muhib.classmate.ui.theme.SmallHeight
import com.nasiat_muhib.classmate.ui.theme.SmallPickerStyle
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.TitleStyle
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace
import java.time.LocalTime

@Composable
fun CustomTimePicker(
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    onShiftClick: (String) -> Unit,
    topPadding: Dp = ZeroSpace,
    bottomPadding: Dp = ZeroSpace,
    startPadding: Dp = SmallSpace,
    endPadding: Dp = SmallSpace,
) {

    val time = LocalTime.now()
    val shift = time.hour < 12
    val currentHour = if(time.hour > 12) time.hour - 12 else if (time.hour == 0) 12 else time.hour
    val hour = currentHour.toString()
    val minute = time.minute.toString()

    val isAmSelected = remember { mutableStateOf(shift) }
    val amContainerColor =
        if (isAmSelected.value) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background
    val amContentColor =
        if (isAmSelected.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    val pmContainerColor =
        if (!isAmSelected.value) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background
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
        Text(text = TIME, color = MaterialTheme.colorScheme.primary, fontSize = SmallPickerStyle.fontSize)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(ExtraSmallSpace),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomBasicTextField(value = hour, onValueChange = onHourChange)
                Text(
                    text = COLON,
                    style = PickerStyle,
                    color = MaterialTheme.colorScheme.primary
                )
                CustomBasicTextField(value = minute, onValueChange = onMinuteChange)
            }

            Spacer(modifier = Modifier.width(MediumSpace))
            Column(
                modifier = Modifier.height(LargeHeight),
                verticalArrangement = Arrangement.spacedBy(ExtraSmallSpace)
            ) {
                Box(modifier = Modifier
                    .clip(LargeRounded)
                    .size(SmallHeight)
                    .background(amContainerColor)
                    .border(1.dp, amContentColor, LargeRounded)
                    .clickable {
                        isAmSelected.value = true
                        onShiftClick(AM)
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = AM, style = SmallPickerStyle, color = amContentColor)
                }

                Box(modifier = Modifier
                    .clip(LargeRounded)
                    .size(SmallHeight)
                    .border(1.dp, pmContentColor, LargeRounded)
                    .background(pmContainerColor)
                    .clickable {
                        isAmSelected.value = false
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
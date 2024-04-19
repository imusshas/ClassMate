package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.nasiat_muhib.classmate.strings.COLON
import com.nasiat_muhib.classmate.strings.DATE
import com.nasiat_muhib.classmate.strings.FORWARD_SLASH
import com.nasiat_muhib.classmate.strings.TIME
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.PickerStyle
import com.nasiat_muhib.classmate.ui.theme.PrimaryRed
import com.nasiat_muhib.classmate.ui.theme.SmallPickerStyle
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.TitleStyle
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace
import java.time.LocalDate

@Composable
fun CustomDatePicker(
    onDayChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
    topPadding: Dp = ZeroSpace,
    bottomPadding: Dp = ZeroSpace,
    startPadding: Dp = MediumSpace,
    endPadding: Dp = MediumSpace,
) {

    val localFocusManager = LocalFocusManager.current

    val date = LocalDate.now()
    val day = rememberSaveable {
        mutableStateOf(date.dayOfMonth.toString())
    }

    val month = rememberSaveable {
        mutableStateOf(date.month.toString().substring(0, 3))
    }
    val year = rememberSaveable {
        mutableStateOf(date.year.toString())
    }


    val dayClicked = rememberSaveable {
        mutableStateOf(false)
    }

    val monthClicked = rememberSaveable {
        mutableStateOf(false)
    }

    val yearClicked = rememberSaveable {
        mutableStateOf(false)
    }

    if (!dayClicked.value) {
        onDayChange(day.value)
    }

    if (!monthClicked.value) {
        onMonthChange(month.value)
    }

    if (!yearClicked.value) {
        onYearChange(year.value)
    }

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
            text = DATE,
            color = PrimaryRed,
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
                CustomBasicTextField(value = day.value, onValueChange = {
                    dayClicked.value = true
                    onDayChange(it)
                })
                Text(
                    text = FORWARD_SLASH,
                    style = PickerStyle,
                    color = PrimaryRed
                )
                CustomBasicTextField(
                    value = month.value,
                    onValueChange = {
                        monthClicked.value = true
                        onMonthChange(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                Text(
                    text = FORWARD_SLASH,
                    style = PickerStyle,
                    color = PrimaryRed
                )
                CustomBasicTextField(
                    value = year.value,
                    onValueChange = {
                        yearClicked.value = true
                        onYearChange(it)
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

        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CustomDatePickerPreview() {
    CustomDatePicker(onDayChange = {}, onMonthChange = {}, onYearChange = {})
}
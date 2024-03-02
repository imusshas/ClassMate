package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.nasiat_muhib.classmate.strings.COLON
import com.nasiat_muhib.classmate.strings.DATE
import com.nasiat_muhib.classmate.strings.FORWARD_SLASH
import com.nasiat_muhib.classmate.strings.TIME
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.PickerStyle
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
    startPadding: Dp = SmallSpace,
    endPadding: Dp = SmallSpace,
) {

    val date = LocalDate.now()
    val day: String = date.dayOfMonth.toString()

    val month: String = date.month.toString().substring(0,3)
    val year: String = date.year.toString()
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
        Text(text = DATE, color = MaterialTheme.colorScheme.primary, fontSize = SmallPickerStyle.fontSize)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(ExtraSmallSpace),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomBasicTextField(value = day, onValueChange = onDayChange)
                Text(
                    text = FORWARD_SLASH,
                    style = PickerStyle,
                    color = MaterialTheme.colorScheme.primary
                )
                CustomBasicTextField(
                    value = month,
                    onValueChange = onMonthChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                Text(
                    text = FORWARD_SLASH,
                    style = PickerStyle,
                    color = MaterialTheme.colorScheme.primary
                )
                CustomBasicTextField(value = year, onValueChange = onYearChange)
            }

        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CustomDatePickerPreview() {
    CustomDatePicker(onDayChange = {}, onMonthChange = {}, onYearChange = {})
}
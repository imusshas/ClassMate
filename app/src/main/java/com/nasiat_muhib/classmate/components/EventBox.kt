package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.ui.theme.ButtonBoldStyle

@Composable
fun EventBox(
    itemList: List<String>,
    selectedItem: String,
    onItemChange: (String) -> Unit,
    onDiscardClick: () -> Unit,
    onPostClick: () -> Unit,
    courseCode: String,
    onCourseCodeChange: (String) -> Unit,
    hour: String,
    minute: String,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    dayNight: Boolean,
    onAmClick: () -> Unit,
    onPmClick: () -> Unit,
    date: String,
    month: String,
    year: String,
    onDateChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
) {
    CustomDialog(
        onDismissRequest = onDiscardClick,
        fraction = 0.95f
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AutoCompleteField(
                itemsList = itemList,
                selectedItem = selectedItem,
                onItemChange = onItemChange,
                label = "Event Type",
                imeAction = ImeAction.Done
            )
            NormalField(
                value = courseCode,
                onValueChange = onCourseCodeChange,
                label = "Course Code",
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )

            TimePickerField(
                hour = hour,
                minute = minute,
                dayNight = dayNight,
                onHourChange = onHourChange,
                onMinuteChange = onMinuteChange,
                onAmClick = onAmClick,
                onPmClick = onPmClick
            )

            DatePickerField(
                date = date,
                month = month,
                year = year,
                onDateChange = onDateChange,
                onMonthChange = onMonthChange,
                onYearChange = onYearChange
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Constants.DISCARD_BUTTON,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onDiscardClick.invoke() },
                    style = ButtonBoldStyle
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = Constants.POST_BUTTON,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onPostClick.invoke() },
                    style = ButtonBoldStyle
                )
            }
        }
    }
}
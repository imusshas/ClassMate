package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants.DATE_PICKER_TITLE
import com.nasiat_muhib.classmate.core.Constants.FORWARD_SLASH
import com.nasiat_muhib.classmate.ui.theme.MediumButtonShape
import com.nasiat_muhib.classmate.ui.theme.PickersStyle
import com.nasiat_muhib.classmate.ui.theme.PickersTitleStyle

@Composable
fun DatePickerField (
    date: String,
    month: String,
    year: String,
    onDateChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = DATE_PICKER_TITLE, color = MaterialTheme.colorScheme.primary, style = PickersTitleStyle)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            NumberTextField(
                number = date,
                onNumberChange = onDateChange,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = FORWARD_SLASH, color = MaterialTheme.colorScheme.primary, style = PickersStyle)
            Spacer(modifier = Modifier.width(4.dp))
            NumberTextField(
                number = month,
                onNumberChange = onMonthChange,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = FORWARD_SLASH, color = MaterialTheme.colorScheme.primary, style = PickersStyle)
            Spacer(modifier = Modifier.width(4.dp))
            NumberTextField(
                number = year,
                onNumberChange = onYearChange,
                imeAction = ImeAction.Done,
                modifier = Modifier
                    .size(80.dp)
            )

        }
    }
}
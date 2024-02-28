package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants.AM
import com.nasiat_muhib.classmate.core.Constants.COLON
import com.nasiat_muhib.classmate.core.Constants.PM
import com.nasiat_muhib.classmate.core.Constants.TIME_PICKER_TITLE
import com.nasiat_muhib.classmate.ui.theme.MediumButtonShape
import com.nasiat_muhib.classmate.ui.theme.PickersStyle
import com.nasiat_muhib.classmate.ui.theme.PickersTitleStyle
import com.nasiat_muhib.classmate.ui.theme.SmallButtonShape

@Composable
fun TimePickerField(
    hour: String,
    minute: String,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    dayNight: Boolean,
    onAmClick: () -> Unit,
    onPmClick: () -> Unit,
) {

    var am by rememberSaveable {
        mutableStateOf(dayNight)
    }
    
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = TIME_PICKER_TITLE, color = MaterialTheme.colorScheme.primary, style = PickersTitleStyle)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            NumberTextField(
                number = hour,
                onNumberChange = onHourChange,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = COLON, color = MaterialTheme.colorScheme.primary, style = PickersStyle)
            Spacer(modifier = Modifier.width(4.dp))
            NumberTextField(
                number = minute,
                onNumberChange = onMinuteChange,
                modifier = Modifier
                    .size(80.dp),
                imeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .width(36.dp)
                    .height(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(MediumButtonShape)
                        .border(
                            BorderStroke(
                                if (am) 1.dp else 0.dp,
                                MaterialTheme.colorScheme.primary
                            ), MediumButtonShape
                        )
                        .clickable {
                            am = true
                            onAmClick.invoke()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = AM,
                        textAlign = TextAlign.Center,
                        color = if (am) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    )
                }

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(MediumButtonShape)
                        .border(
                            BorderStroke(
                                if (!am) 1.dp else 0.dp,
                                MaterialTheme.colorScheme.primary
                            ), MediumButtonShape
                        )
                        .clickable {
                            am = false
                            onPmClick.invoke()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = PM,
                        textAlign = TextAlign.Center,
                        color = if (!am) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    )
                }

            }
        }
    }
    
}
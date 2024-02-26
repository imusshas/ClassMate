package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants.AM
import com.nasiat_muhib.classmate.core.Constants.KEYBOARD_ARROW_DOWN_ICON
import com.nasiat_muhib.classmate.core.Constants.KEYBOARD_ARROW_UP_ICON
import com.nasiat_muhib.classmate.core.UtilityVariables.AM_PM
import com.nasiat_muhib.classmate.core.UtilityVariables.DAYS_OF_WEEK
import com.nasiat_muhib.classmate.core.UtilityVariables.HOURS_OF_DAY
import com.nasiat_muhib.classmate.core.UtilityVariables.MINUTES_OF_HOUR
import com.nasiat_muhib.classmate.data.model.DayTime

@Composable
fun dayTimePicker(
    modifier: Modifier = Modifier
): DayTime {

    var weekDayIndex by rememberSaveable { mutableIntStateOf(0) }
    var hourIndex by rememberSaveable { mutableIntStateOf(0) }
    var minuteIndex by rememberSaveable { mutableIntStateOf(0) }
    var amPmIndex by rememberSaveable { mutableIntStateOf(0) }

    var weekDay by rememberSaveable { mutableStateOf(DAYS_OF_WEEK[0]) }
    var hour by rememberSaveable { mutableIntStateOf(HOURS_OF_DAY[0]) }
    var minute by rememberSaveable { mutableIntStateOf(MINUTES_OF_HOUR[0]) }
    var amPm by rememberSaveable { mutableStateOf(AM_PM[0]) }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // WeekDay
        ColumnComponent(
            title = "WeekDay",
            data = DAYS_OF_WEEK,
            index = weekDayIndex,
            onUpClick = {
                if (weekDayIndex > 0) {
                    weekDayIndex -= 1
                } else {
                    weekDayIndex = DAYS_OF_WEEK.size - 1
                }

                weekDay = DAYS_OF_WEEK[weekDayIndex]
            },
            onDownClick = {
                if (weekDayIndex < DAYS_OF_WEEK.size - 1) {
                    weekDayIndex += 1
                } else {
                    weekDayIndex = 0
                }

                weekDay = DAYS_OF_WEEK[weekDayIndex]
            },
            modifier = Modifier.weight(1f)
        )

        // Hour
        ColumnComponent(
            title = "Hour",
            data = HOURS_OF_DAY,
            index = hourIndex,
            onUpClick = {
                if (hourIndex > 0) {
                    hourIndex -= 1
                } else {
                    hourIndex = HOURS_OF_DAY.size - 1
                }

                hour = HOURS_OF_DAY[hourIndex]
            },
            onDownClick = {
                if (hourIndex < HOURS_OF_DAY.size - 1) {
                    hourIndex += 1
                } else {
                    hourIndex = 0
                }

                hour = HOURS_OF_DAY[hourIndex]
            },
            modifier = Modifier.weight(1f)
        )

        // Minute
        ColumnComponent(
            title = "Minute",
            data = MINUTES_OF_HOUR,
            index = minuteIndex,
            onUpClick = {
                if (minuteIndex > 0) {
                    minuteIndex -= 1
                } else {
                    minuteIndex = MINUTES_OF_HOUR.size - 1
                }

                minute = MINUTES_OF_HOUR[minuteIndex]
            },
            onDownClick = {
                if (minuteIndex < MINUTES_OF_HOUR.size - 1) {
                    minuteIndex += 1
                } else {
                    minuteIndex = 0
                }

                minute = MINUTES_OF_HOUR[minuteIndex]
            },
            modifier = Modifier.weight(1f)
        )

        // Am/Pm
        ColumnComponent(
            title = "Am/Pm",
            data = AM_PM,
            index = amPmIndex,
            onUpClick = {
                if (amPmIndex > 0) {
                    amPmIndex -= 1
                } else {
                    amPmIndex = AM_PM.size - 1
                }

                amPm = AM_PM[amPmIndex]
            },
            onDownClick = {
                if (amPmIndex < AM_PM.size - 1) {
                    amPmIndex += 1
                } else {
                    amPmIndex = 0
                }

                amPm = AM_PM[amPmIndex]
            },
            modifier = Modifier.weight(1f)
        )

    }

    return DayTime(weekDay = weekDay, hour = hour, minute = minute, amPm = amPm)
}

@Composable
private fun ColumnComponent(
    title: String,
    data: List<Any>,
    index: Int,
    onUpClick: () -> Unit,
    onDownClick: () -> Unit,
    modifier: Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = title)
        IconButton(
            onClick = onUpClick,
            modifier = Modifier.height(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = KEYBOARD_ARROW_UP_ICON,
                modifier = Modifier.height(16.dp)
            )
        }
        Text(text = data[index].toString())
        IconButton(
            onClick = onDownClick,
            modifier = Modifier.height(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = KEYBOARD_ARROW_DOWN_ICON,
                modifier = Modifier.height(16.dp)
            )
        }
    }
}
package com.nasiat_muhib.classmate.presentation.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.EventBox
import com.nasiat_muhib.classmate.components.UpdateBox
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.ClassMateAppScreen
import com.nasiat_muhib.classmate.ui.theme.ButtonBoldStyle
import com.nasiat_muhib.classmate.ui.theme.MediumButtonShape
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun HomeScreenContent(
    navigateToTab: (TabItem) -> Unit
) {

    val dateTime = LocalDateTime.now()

    var update by rememberSaveable { mutableStateOf("") }

    val eventTypes = listOf("Term Test", "Assignment")
    var eventType by rememberSaveable { mutableStateOf(eventTypes[0]) }
    var courseCode by rememberSaveable { mutableStateOf("") }

    val dayNight = dateTime.hour <= 12

    val currentHour = if(dateTime.hour > 12) {dateTime.hour -12} else if (dateTime.hour == 0) 12 else dateTime.hour

    var hour by rememberSaveable { mutableStateOf(currentHour.toString()) }
    var minute by rememberSaveable { mutableStateOf(dateTime.minute.toString()) }
    var shift by rememberSaveable { mutableStateOf(if(dateTime.hour > 11) "Pm" else "Am") }



    var date by rememberSaveable { mutableStateOf(dateTime.dayOfMonth.toString()) }
    var month by rememberSaveable { mutableStateOf(dateTime.monthValue.toString()) }
    var year by rememberSaveable { mutableStateOf(dateTime.year.toString()) }


    var showUpdateBox by rememberSaveable { mutableStateOf(false) }
    var showEventBox by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        ClassMateAppScreen(
            tab = TabItem.Home,
            navigateToTab = { tabItem -> navigateToTab.invoke(tabItem) }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 48.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {

            TodayClasses()

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MediumButtonShape, // TODO: Recheck the shape
//                elevation = CardDefaults.cardElevation(15.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CourseAndRequestTitle()
                    Text(text = "Course & Request Section")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ElevatedButton(
                    onClick = { showUpdateBox = true },
                    shape = MediumButtonShape,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Add Update", style = ButtonBoldStyle)
                }

                ElevatedButton(
                    onClick = { showEventBox = true },
                    shape = MediumButtonShape,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Add Event", style = ButtonBoldStyle)
                }
            }
        }
    }



    if (showUpdateBox) {
        UpdateBox(
            update = update,
            onUpdateChange = { update = it },
            label = "Update",
            onDiscardClick = { showUpdateBox = false },
            onPostClick = { showUpdateBox = false }
        )
    }

    if (showEventBox) {
        EventBox(
            itemList = eventTypes,
            selectedItem = eventType,
            onItemChange = { eventType = it },
            onDiscardClick = { showEventBox = false },
            onPostClick = { showEventBox = false },
            courseCode = courseCode,
            onCourseCodeChange = { courseCode = it },
            hour = hour,
            minute = minute,
            date = date,
            month = month,
            year = year,
            onHourChange = { hour = it },
            onMinuteChange = { minute = it },
            dayNight = dayNight,
            onAmClick = { shift = "Am" },
            onPmClick = { shift = "Pm" },
            onDateChange = { date = it },
            onMonthChange = { month = it },
            onYearChange = { year = it }
        )
    }
}
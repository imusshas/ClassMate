package com.nasiat_muhib.classmate.presentation.main.menu.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.CustomDropDownMenu
import com.nasiat_muhib.classmate.core.Constants.WEEK_DAYS
import com.nasiat_muhib.classmate.domain.event.RoutineUIEvent
import com.nasiat_muhib.classmate.presentation.main.menu.routine.components.RoutineTopBar
import com.nasiat_muhib.classmate.strings.COLON
import com.nasiat_muhib.classmate.strings.SEARCH
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallHeight
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.SomeStyle

@Composable
fun RoutineScreen(
    routineViewModel: RoutineViewModel = hiltViewModel(),
    navigateBackToMenu: () -> Unit
) {

    val searchText = routineViewModel.searchText.collectAsState()
    val classes = routineViewModel.classes.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        RoutineTopBar(onBackIconClick = navigateBackToMenu)
        Spacer(modifier = Modifier.height(MediumSpace))
        CustomDropDownMenu(
            itemList = WEEK_DAYS,
            onItemChange = { weekDay ->
                routineViewModel.onRoutineEvent(RoutineUIEvent.WeekDayChanged(weekDay))
            }
        )
        Spacer(modifier = Modifier.height(SmallSpace))
        TextField(
            value = searchText.value,
            onValueChange = { searchText ->
                routineViewModel.onRoutineEvent(RoutineUIEvent.SearchTextChanged(searchText))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = SEARCH) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(MediumSpace))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(ExtraSmallSpace)
        ) {
            items(
                items = classes.value.toList(),
                key = {
                    "${it.classDepartment}:${it.classCourseCode}:${it.classNo}"
                }
            ) { classDetails ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(NormalHeight),
                    shape = LargeRounded
                ) {
                    Row(
                        modifier = Modifier
                            .clip(LargeRounded)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = classDetails.weekDay, style = SomeStyle, modifier = Modifier.weight(1f))
                        Text(text = classDetails.classroom, style = SomeStyle, modifier = Modifier.weight(1f))
                        Text(text = classDetails.section, style = SomeStyle, modifier = Modifier.weight(1f))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = classDetails.startHour.toString(), style = SomeStyle)
                            Text(text = COLON, style = SomeStyle)
                            Text(text = classDetails.startMinute.toString(), style = SomeStyle)
                            Spacer(modifier = Modifier.width(SmallSpace))
                            Text(text = classDetails.startShift, style = SomeStyle)
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = classDetails.endHour.toString(), style = SomeStyle)
                            Text(text = COLON, style = SomeStyle)
                            Text(text = classDetails.endMinute.toString(), style = SomeStyle)
                            Spacer(modifier = Modifier.width(SmallSpace))
                            Text(text = classDetails.endShift, style = SomeStyle)
                        }
                    }
                }
            }
        }
    }
}
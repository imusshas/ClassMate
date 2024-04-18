package com.nasiat_muhib.classmate.presentation.main.home.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.HomeUIEvent
import com.nasiat_muhib.classmate.presentation.main.home.HomeViewModel
import com.nasiat_muhib.classmate.strings.CLASS_REPRESENTATIVE
import com.nasiat_muhib.classmate.strings.COLON
import com.nasiat_muhib.classmate.strings.TAG
import com.nasiat_muhib.classmate.strings.TEACHER
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.PrimaryRed
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.SomeStyle

@Composable
fun ClassDisplay(
    recomposeHomeScreen: () -> Unit,
    homeViewModel: HomeViewModel,
    classDetails: ClassDetails,
    user: User
) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace),
        shape = LargeRounded
    ) {
        Row(
            modifier = Modifier
                .clip(LargeRounded)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = classDetails.classCourseCode)

            // Start Time
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = classDetails.startHour.toString(), style = SomeStyle)
                Text(text = COLON, style = SomeStyle)
                Text(text = classDetails.startMinute.toString(), style = SomeStyle)
                Spacer(modifier = Modifier.width(SmallSpace))
                Text(text = classDetails.startShift, style = SomeStyle)
            }

            // End Time
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = classDetails.endHour.toString(), style = SomeStyle)
                Text(text = COLON, style = SomeStyle)
                Text(text = classDetails.endMinute.toString(), style = SomeStyle)
                Spacer(modifier = Modifier.width(SmallSpace))
                Text(text = classDetails.endShift, style = SomeStyle)
            }

            Text(text = classDetails.classroom)

            Switch(
                checked = classDetails.isActive,
                onCheckedChange = {activeStatus ->
                    if (user.role == TEACHER || user.role == CLASS_REPRESENTATIVE) {
                        homeViewModel.onHomeEvent(
                            HomeUIEvent.ClassStatusChange(
                                classDetails,
                                activeStatus
                            )
                        )
                    }
                    recomposeHomeScreen()
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = PrimaryRed
                )
            )
        }
    }
}
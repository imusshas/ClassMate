package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.event.CreateCourseUIEvent
import com.nasiat_muhib.classmate.strings.COLON
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallHeight
import com.nasiat_muhib.classmate.ui.theme.LargeHeight
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.SomeStyle
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun EditClassDetails(
    classDetails: ClassDetails,
    createCourseViewModel: CreateCourseViewModel,
    animationDuration: Int = 500
) {

    val isVisible = rememberSaveable { mutableStateOf(true) }

    val delete = SwipeAction(
        onSwipe = {
            createCourseViewModel.onCreateCourse(
                CreateCourseUIEvent.ClassDetailsDeleteSwipe(classDetails)
            )
            isVisible.value = false
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.delete),
//                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(ExtraSmallHeight)
            )
        },
        background = Color.Red
    )

    SwipeableActionsBox(
        endActions = listOf(delete),
        swipeThreshold = LargeHeight,
        modifier = Modifier
            .clip(LargeRounded)
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace),
    ) {
        AnimatedVisibility(visible = isVisible.value,) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize(),
                shape = LargeRounded
            ) {
                Row(
                    modifier = Modifier
                        .clip(LargeRounded)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = classDetails.weekDay, style = SomeStyle)
                    Text(text = classDetails.classroom, style = SomeStyle)
                    Text(text = classDetails.section, style = SomeStyle)
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
                }
            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditClassPreview() {
//    EditClassDetails(classDetails = ClassDetails("CSE","CSE 252", 0,"Sun","Gallery 2", "Both", 12, 0, "Pm", 1, 0, "Pm"))
}
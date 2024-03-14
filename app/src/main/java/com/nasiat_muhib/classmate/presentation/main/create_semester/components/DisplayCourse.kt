package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SmartDisplay
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
import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.domain.event.CreateSemesterUIEvent
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterViewModel
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallHeight
import com.nasiat_muhib.classmate.ui.theme.LargeHeight
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun DisplayCourse(
    course: Course,
    createSemesterViewModel: CreateSemesterViewModel
) {
    val isVisible = rememberSaveable { mutableStateOf(true) }

    val display = SwipeAction(
        onSwipe = {
            createSemesterViewModel.onCreateSemesterEvent(CreateSemesterUIEvent.DisplayCourseSwipe(course))
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.SmartDisplay,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(ExtraSmallHeight)
            )
        },
        background = Color.Red
    )

    val delete = SwipeAction(
        onSwipe = {
            createSemesterViewModel.onCreateSemesterEvent(CreateSemesterUIEvent.DeleteCourseSwipe(course))
            isVisible.value = false
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(ExtraSmallHeight)
            )
        },
        background = Color.Red
    )

    SwipeableActionsBox(
        startActions = listOf(display),
        endActions = listOf(delete),
        swipeThreshold = LargeHeight,
        modifier = Modifier
            .clip(LargeRounded)
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace),
    ) {
        AnimatedVisibility(
            visible = isVisible.value,
            exit = shrinkHorizontally()
        ) {
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
                    Text(text = course.courseCode)
                    Text(text = course.courseTitle)
                    Text(text = course.courseCredit.toString())
                }
            }
        }
    }

}
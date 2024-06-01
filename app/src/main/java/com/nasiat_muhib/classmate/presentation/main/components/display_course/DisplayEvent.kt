package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.data.model.Event
import com.nasiat_muhib.classmate.domain.event.CourseDetailsDisplayUIEvent
import com.nasiat_muhib.classmate.strings.COLON
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallHeight
import com.nasiat_muhib.classmate.ui.theme.LargeHeight
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.SomeStyle
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun DisplayEvent(
    event: Event,
    courseDetailsDisplayViewModel: CourseDetailsDisplayViewModel,
    isSwipeAble: Boolean,
    isVisible: Boolean
) {



    val delete = SwipeAction(
        onSwipe = {
            courseDetailsDisplayViewModel.onDisplayEvent(
                CourseDetailsDisplayUIEvent.EventDeleteSwipe(
                    event
                )
            )
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

    if (isSwipeAble) {
        SwipeableActionsBox(
            endActions = listOf(delete),
            swipeThreshold = LargeHeight,
            modifier = Modifier
                .clip(LargeRounded)
                .fillMaxWidth()
                .height(NormalHeight)
                .padding(horizontal = MediumSpace),
        ) {
            AnimatedVisibility(
                visible = isVisible,
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
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = event.day.toString(), style = SomeStyle)
                            Text(text = COLON, style = SomeStyle)
                            Text(text = event.month, style = SomeStyle)
                            Text(text = COLON, style = SomeStyle)
                            Text(text = event.year.toString(), style = SomeStyle)
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = event.hour.toString(), style = SomeStyle)
                            Text(text = COLON, style = SomeStyle)
                            Text(text = event.minute.toString(), style = SomeStyle)
                            Spacer(modifier = Modifier.width(SmallSpace))
                            Text(text = event.shift, style = SomeStyle)
                        }
                        Text(text = event.classroom, style = SomeStyle)
                    }
                }
            }
        }
    } else {
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
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = event.day.toString(), style = SomeStyle)
                    Text(text = COLON, style = SomeStyle)
                    Text(text = event.month, style = SomeStyle)
                    Text(text = COLON, style = SomeStyle)
                    Text(text = event.year.toString(), style = SomeStyle)
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = event.hour.toString(), style = SomeStyle)
                    Text(text = COLON, style = SomeStyle)
                    Text(text = event.minute.toString(), style = SomeStyle)
                    Spacer(modifier = Modifier.width(SmallSpace))
                    Text(text = event.shift, style = SomeStyle)
                }
                Text(text = event.classroom, style = SomeStyle)
            }
        }
    }

}
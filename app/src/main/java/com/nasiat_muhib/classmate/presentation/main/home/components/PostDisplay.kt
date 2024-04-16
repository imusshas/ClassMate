package com.nasiat_muhib.classmate.presentation.main.home.components

import android.icu.text.SimpleDateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import com.nasiat_muhib.classmate.data.model.Post
import com.nasiat_muhib.classmate.domain.event.HomeUIEvent
import com.nasiat_muhib.classmate.presentation.main.home.HomeViewModel
import com.nasiat_muhib.classmate.strings.AM
import com.nasiat_muhib.classmate.strings.PM
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallHeight
import com.nasiat_muhib.classmate.ui.theme.LargeHeight
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.PostHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.util.Date
import java.util.Locale

@Composable
fun PostDisplay(
    recomposeHomeScreen: () -> Unit,
    post: Post,
    homeViewModel: HomeViewModel,
    isCreator: Boolean,
) {
    val isVisible = rememberSaveable { mutableStateOf(true) }

    val delete = SwipeAction(
        onSwipe = {
            homeViewModel.onHomeEvent(HomeUIEvent.DeletePostSwipe(post))
            isVisible.value = false
            recomposeHomeScreen()
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

    if (isCreator) {
        SwipeableActionsBox(
            endActions = listOf(delete),
            swipeThreshold = LargeHeight,
            modifier = Modifier
                .clip(LargeRounded)
                .fillMaxWidth()
                .height(PostHeight)
                .padding(horizontal = MediumSpace),
        ) {
            AnimatedVisibility(
                visible = isVisible.value,
                exit = shrinkHorizontally()
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(SmallSpace),
                    shape = RectangleShape
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(SmallSpace)) {
                        val date = Date(post.timestamp)
                        val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                        val dateString = dateFormatter.format(date)

                        val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                        val timeString = timeFormatter.format(date)

                        val hours = timeString.substringBefore(":").toInt()
                        val hour = if (hours > 12) hours - 12 else if (hours == 0) 12 else hours

                        val minute = timeString.substringAfter(":").toInt()
                        val timeFormatterForSecond = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                        val timeStringForSecond = timeFormatterForSecond.format(date)
                        val second = timeStringForSecond.substringAfterLast(":").toInt()

                        val shift = if (hours < 12) AM else PM
                        val courseCode = post.courseCode.substringBefore(":").trim()
                        val courseTitle = post.courseCode.substringAfter(":").trim()
                        Text(text = "${post.firstName} ${post.lastName}", fontWeight = FontWeight.Black)
                        Text(text = dateString, fontWeight = FontWeight.Light)
                        Text(text = "$hour:$minute:$second $shift", fontWeight = FontWeight.Light)
                        Spacer(modifier = Modifier.height(LargeSpace))
                        Text(text = courseCode, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = courseTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = post.description)
                    }
                }
            }
        }
    } else if (isVisible.value) {
        Box(
            modifier = Modifier
                .clip(LargeRounded)
                .fillMaxWidth()
                .height(PostHeight)
                .padding(horizontal = MediumSpace),
            contentAlignment = Alignment.Center
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SmallSpace),
                shape = RectangleShape
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(SmallSpace)) {
                    val date = Date(post.timestamp)
                    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val dateString = dateFormatter.format(date)

                    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val timeString = timeFormatter.format(date)

                    val hours = timeString.substringBefore(":").toInt()
                    val hour = if (hours > 12) hours - 12 else if (hours == 0) 12 else hours

                    val minute = timeString.substringAfter(":").toInt()
                    val timeFormatterForSecond = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    val timeStringForSecond = timeFormatterForSecond.format(date)
                    val second = timeStringForSecond.substringAfterLast(":").toInt()

                    val shift = if (hours < 12) AM else PM
                    Text(text = "${post.firstName} ${post.lastName}", fontWeight = FontWeight.Black)
                    Text(text = dateString, fontWeight = FontWeight.Light)
                    Text(text = "$hour:$minute:$second $shift", fontWeight = FontWeight.Light)
                    Spacer(modifier = Modifier.height(LargeSpace))
                    Text(text = post.courseCode, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = post.description)
                }
            }
        }
    }
}
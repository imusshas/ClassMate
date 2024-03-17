package com.nasiat_muhib.classmate.presentation.main.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SmartDisplay
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
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.Post
import com.nasiat_muhib.classmate.domain.event.HomeUIEvent
import com.nasiat_muhib.classmate.navigation.Screen
import com.nasiat_muhib.classmate.presentation.main.home.HomeViewModel
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallHeight
import com.nasiat_muhib.classmate.ui.theme.LargeHeight
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.PostHeight
import com.nasiat_muhib.classmate.ui.theme.SmallRounded
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun PostDisplay(
    post: Post,
    homeViewModel: HomeViewModel,
    isCreator: Boolean,
) {
    val isVisible = rememberSaveable { mutableStateOf(true) }

    val delete = SwipeAction(
        onSwipe = {
//            homeViewModel.onHomeEvent(HomeUIEvent.DeleteCourseRequest(course))
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
                        Text(text = "${post.firstName} ${post.firstName}", fontWeight = FontWeight.Black)
                        Text(text = post.timestamp.toString(), fontWeight = FontWeight.Light)
                        Spacer(modifier = Modifier.height(LargeSpace))
                        Text(text = post.courseCode, style = MaterialTheme.typography.titleMedium)
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
                    Text(text = "${post.firstName} ${post.firstName}", fontWeight = FontWeight.Black)
                    Text(text = post.timestamp.toString(), fontWeight = FontWeight.Light)
                    Spacer(modifier = Modifier.height(LargeSpace))
                    Text(text = post.courseCode, style = MaterialTheme.typography.titleMedium)
                    Text(text = post.description)
                }
            }
        }
    }
}
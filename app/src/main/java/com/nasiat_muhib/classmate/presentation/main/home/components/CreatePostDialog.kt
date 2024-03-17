package com.nasiat_muhib.classmate.presentation.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.CustomClickableText
import com.nasiat_muhib.classmate.components.CustomDialog
import com.nasiat_muhib.classmate.components.CustomLargeTextField
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.CustomTextField
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.PostUIEvent
import com.nasiat_muhib.classmate.presentation.main.home.HomeViewModel
import com.nasiat_muhib.classmate.strings.COURSE_CODE
import com.nasiat_muhib.classmate.strings.COURSE_CODE_LABEL
import com.nasiat_muhib.classmate.strings.DISCARD_BUTTON
import com.nasiat_muhib.classmate.strings.POST_BUTTON
import com.nasiat_muhib.classmate.ui.theme.ExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace
import java.sql.Timestamp

@Composable
fun CreatePostDialog(
    homeViewModel: HomeViewModel,
    user: User,
) {

    val createPostUIState by homeViewModel.createPostUIState.collectAsState()

    CustomDialog(onDismissRequest = { homeViewModel.onPostEvent(PostUIEvent.DiscardButtonClicked) }) {
        Column {
            CustomTextField(labelValue = COURSE_CODE_LABEL, onValueChange = { courseCode ->
                homeViewModel.onPostEvent(PostUIEvent.CourseCodeChanged(courseCode))
            }, startPadding = ZeroSpace, endPadding = ZeroSpace, errorMessage = createPostUIState.courseCodeError)
            Spacer(modifier = Modifier.height(SmallSpace))
            CustomLargeTextField(
                placeholderValue = "What's on your mind?",
                onValueChange = { description ->
                    homeViewModel.onPostEvent(PostUIEvent.DescriptionChanged(description))
                },
                errorMessage = createPostUIState.descriptionError
            )

            Spacer(modifier = Modifier.height(ExtraLargeSpace))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumSpace),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomClickableText(text = DISCARD_BUTTON, onClick = {
                    homeViewModel.onPostEvent(PostUIEvent.DiscardButtonClicked)
                })
                Spacer(modifier = Modifier.width(SmallSpace))
                CustomClickableText(text = POST_BUTTON, onClick = {
                    homeViewModel.onPostEvent(
                        PostUIEvent.PostButtonClicked(
                            timestamp = Timestamp(System.currentTimeMillis()),
                            creator = user.email,
                            firstName = user.firstName,
                            lastName = user.lastName
                        )
                    )
                })
            }
            Spacer(modifier = Modifier.height(ExtraLargeSpace))
        }
    }
}
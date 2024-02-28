package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.core.Constants.CLOSE_ICON
import com.nasiat_muhib.classmate.core.Constants.CREATE_COURSE_TITLE
import com.nasiat_muhib.classmate.ui.theme.ButtonBoldStyle

@Composable
fun CreateCourseTopBar(
    onCloseClick: () -> Unit,
    onCheckClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = CLOSE_ICON,
            modifier = Modifier.clickable {
                onCloseClick.invoke()
            }
        )

        Text(text = CREATE_COURSE_TITLE, style = ButtonBoldStyle)

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = CLOSE_ICON,
            modifier = Modifier.clickable {
                onCheckClick.invoke()
            }
        )
    }
}
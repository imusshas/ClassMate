package com.nasiat_muhib.classmate.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.TitleBox

@Composable
fun CourseAndRequestTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleBox(
            title = "Your Courses",
            titleColor = Color.White,
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 15.dp))
                .fillMaxWidth()
                .background(Color.Black)
                .height(48.dp)
                .weight(1f)
        )

        VerticalDivider(thickness = 1.dp, color = Color.White, modifier = Modifier.height(48.dp))

        TitleBox(
            title = "Course Requests",
            titleColor = Color.White,
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 15.dp))
                .fillMaxWidth()
                .background(Color.Black)
                .height(48.dp)
                .weight(1f)
        )
    }
}
package com.nasiat_muhib.classmate.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.TitleBox

@Composable
fun TodayClasses() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
//                elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleBox(
                title = "Today's Classes",
                titleColor = Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                    .fillMaxWidth()
                    .background(Color.Black)
                    .height(48.dp)
            )

            Text(text = "No Class")
        }
    }
}
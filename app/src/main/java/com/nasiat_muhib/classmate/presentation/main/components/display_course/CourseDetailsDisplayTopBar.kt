package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallHeight

@Composable
fun CourseDetailsDisplayTopBar(
    courseCode: String,
    onBackIconClick: () -> Unit,
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = null,
            modifier = Modifier.clickable {
                onBackIconClick()
            }
        )

        Text(text = courseCode)

        Image(
            painter = painterResource(id = R.drawable.logo_with_background),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(CircleShape)
                .size(SmallHeight)
        )

    }

}
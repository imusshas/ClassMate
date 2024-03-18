package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.components.CustomClickableLinks
import com.nasiat_muhib.classmate.data.model.ResourceLink
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight

@Composable
fun DisplayResourceLink(resourceLink: ResourceLink) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(horizontal = ExtraSmallSpace),
            contentAlignment = Alignment.Center
        ) {
            CustomClickableLinks(text = resourceLink.title, annotation = resourceLink.link)
        }
    }
}
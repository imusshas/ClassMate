package com.nasiat_muhib.classmate.presentation.main.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.ClassMateAppScreen

@Composable
fun NotificationScreen(navigateToTab: (TabItem) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        ClassMateAppScreen(
            tab = TabItem.Notification,
            navigateToTab = { tabItem -> navigateToTab.invoke(tabItem) })
        Text(text = TabItem.Notification.name)
    }
}
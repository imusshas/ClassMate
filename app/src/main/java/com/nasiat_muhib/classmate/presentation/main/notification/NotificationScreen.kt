package com.nasiat_muhib.classmate.presentation.main.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow

@Composable
fun NotificationScreen(
    notificationViewModel: NotificationViewModel = hiltViewModel(),
    navigateToTab: (TabItem) -> Unit,
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        ClassMateTabRow(tab = TabItem.Notification, navigateToTab = navigateToTab)
        CustomElevatedButton(
            text = "Fire Notification",
            onClick = {
                notificationViewModel.showSimpleNotification(context)
            }
        )
    }
}
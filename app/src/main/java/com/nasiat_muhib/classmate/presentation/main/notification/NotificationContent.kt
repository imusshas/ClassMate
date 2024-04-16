package com.nasiat_muhib.classmate.presentation.main.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.navigation.NavigationViewModel
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.strings.CLASS_CANCELLED
import com.nasiat_muhib.classmate.strings.CLASS_UPDATE
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraSmallSpace

@Composable
fun NotificationContent(
    navigateToTab: (TabItem) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToCourseDetailsScreen: () -> Unit,
    notificationViewModel: NotificationViewModel,
    navigationViewModel: NavigationViewModel,
    user: User,
) {
    notificationViewModel.getNotifications(user.email)
    notificationViewModel.getCourses(user.courses)
    val notifications by notificationViewModel.notifications.collectAsState()
    val courses by notificationViewModel.courses.collectAsState()

    Column (modifier = Modifier.fillMaxSize()) {
        ClassMateTabRow(tab = TabItem.Notification, navigateToTab = navigateToTab)
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = notifications) { notification ->
                Card(
                    onClick = {
                              if (notification.type == CLASS_CANCELLED || notification.type == CLASS_UPDATE) {
                                  navigateToHomeScreen()
                              } else {
                                  notificationViewModel.getCourses(user.courses)
                                  courses.forEach {
                                      if (it.courseCode == notification.courseCode && it.courseDepartment == notification.courseDepartment) {
                                          navigationViewModel.setCourse(it)
                                          navigateToCourseDetailsScreen()
                                      }
                                  }
                              }
                    },
                    modifier = Modifier.background(
                        if (notification.isRead) {
                            Color.White
                        } else {
                            Color.LightGray
                        }
                    )
                ) {
                    Text(text = notification.type, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Department: ", fontWeight = FontWeight.Bold)
                        Text(text = notification.courseDepartment)
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Course Code: ", fontWeight = FontWeight.Bold)
                        Text(text = notification.courseCode)
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Title: ", fontWeight = FontWeight.Bold)
                        Text(text = notification.courseTitle)
                    }
                    HorizontalDivider(thickness = ExtraExtraSmallSpace)
                }
            }
        }
    }
}
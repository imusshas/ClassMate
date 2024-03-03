package com.nasiat_muhib.classmate.presentation.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import com.nasiat_muhib.classmate.navigation.TabItem

@Composable
fun ClassMateTabRow(
    tab: TabItem
) {

    Row(modifier = Modifier.fillMaxWidth()) {
        TabItem.entries.forEach { tabItem ->
            Tab(
                selected = tab == tabItem,
                onClick = {
                          when(tabItem) {
                              TabItem.Home -> ClassMateAppRouter.navigateTo(Screen.HomeScreen)
                              TabItem.CreateSemester -> ClassMateAppRouter.navigateTo(Screen.CreateSemesterScreen)
                              TabItem.EnrollCourse -> ClassMateAppRouter.navigateTo(Screen.EnrollCourseScreen)
                              TabItem.Notification -> ClassMateAppRouter.navigateTo(Screen.NotificationScreen)
                              TabItem.Menu -> ClassMateAppRouter.navigateTo(Screen.MenuScreen)
                          }
                },
                modifier = Modifier.weight(1f)
            ) {
                Column {
                    Icon(
                        painter = painterResource(id = tabItem.iconId),
                        contentDescription = tabItem.name,
                        modifier = Modifier
                            .padding(24.dp),
                        tint = if (tabItem == tab) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                    if(tab == tabItem) {
                        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
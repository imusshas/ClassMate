package com.nasiat_muhib.classmate.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.navigation.TabItem


@Composable
fun ClassMateAppScreen(tab: TabItem, navigateToTab: (TabItem) -> Unit) {

    Row(modifier = Modifier.fillMaxWidth()) {
        TabItem.entries.forEach { tabItem ->
            Tab(
                selected = tab == tabItem,
                onClick = { navigateToTab(tabItem) },
                modifier = Modifier.weight(1f)
            ) {
                Column {
                    Icon(
                        painter = painterResource(id = tabItem.iconId),
                        contentDescription = tabItem.name,
                        modifier = Modifier
                            .padding(24.dp),
                        tint = if (tabItem == tab) {
                            Color.Red
                        } else {
                            Color.Black
                        }
                    )
                    if(tab == tabItem) {
                        Divider(thickness = 1.dp, color = Color.Red)
                    }
                }
            }
        }
    }
}
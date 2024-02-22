package com.nasiat_muhib.classmate.presentation.main.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.navigation.MenuItem
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.ClassMateAppScreen
import com.nasiat_muhib.classmate.presentation.main.menu.MenuViewModel

@Composable
fun MenuContent(
    menuViewModel: MenuViewModel,
    navigateToTab: (TabItem) -> Unit,
    navigateToMenu: (MenuItem) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        ClassMateAppScreen(
            tab = TabItem.Menu,
            navigateToTab = { tabItem -> navigateToTab.invoke(tabItem) }
        )

        MenuItemContainer(menuViewModel = menuViewModel, navigateToMenu = {menuItem -> navigateToMenu.invoke(menuItem) })

    }
}
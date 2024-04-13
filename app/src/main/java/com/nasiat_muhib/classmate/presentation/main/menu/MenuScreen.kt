package com.nasiat_muhib.classmate.presentation.main.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.domain.event.MenuUIEvent
import com.nasiat_muhib.classmate.navigation.MenuItem
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.components.ClassMateTabRow
import com.nasiat_muhib.classmate.presentation.main.menu.components.MenuItem
import com.nasiat_muhib.classmate.presentation.main.menu.components.SignOutItem
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight

@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = hiltViewModel(),
    navigateToTab: (TabItem) -> Unit,
    navigateToMenu: (MenuItem) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClassMateTabRow(tab = TabItem.Menu, navigateToTab = navigateToTab)
        Spacer(modifier = Modifier.height(NormalHeight))
        MenuItem(menuItem = MenuItem.Profile, onClick = {
            navigateToMenu(MenuItem.Profile)
        })
        Spacer(modifier = Modifier.height(MediumSpace))
        MenuItem(menuItem = MenuItem.Routine, onClick = {
            navigateToMenu(MenuItem.Routine)
        })
        Spacer(modifier = Modifier.height(MediumSpace))
        SignOutItem(menuItem = MenuItem.SignOut, onClick = {
            menuViewModel.onEvent(MenuUIEvent.SignOutButtonClicked)
            navigateToMenu(MenuItem.SignOut)
        })
    }
}
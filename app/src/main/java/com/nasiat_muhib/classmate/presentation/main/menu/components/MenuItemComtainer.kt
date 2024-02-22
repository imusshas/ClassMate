package com.nasiat_muhib.classmate.presentation.main.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.navigation.MenuItem
import com.nasiat_muhib.classmate.presentation.main.menu.MenuViewModel

@Composable
fun MenuItemContainer(
    menuViewModel: MenuViewModel,
    navigateToMenu: (MenuItem) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        MenuItem.entries.forEach { menuItem ->
            ElevatedButton(
                onClick = {
                    if(menuItem == MenuItem.SignOut) {
                        menuViewModel.signOut()
                    }
                    navigateToMenu.invoke(menuItem)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = menuItem.iconId),
                        contentDescription = menuItem.title,
                    )
                    Text(text = menuItem.title)
                }
            }
        }
    }
}
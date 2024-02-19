package com.nasiat_muhib.classmate.presentation.main.menu

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.navigation.MenuItem
import com.nasiat_muhib.classmate.navigation.Routes
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.auth.sign_in.SignInViewModel
import com.nasiat_muhib.classmate.presentation.main.ClassMateAppScreen

@Composable
fun MenuScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    navigateToTab: (TabItem) -> Unit,
    navigateToMenu: (MenuItem) -> Unit,
    signOut: () -> Unit
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
                        if (menuItem == MenuItem.LogOut) {
                            signInViewModel.signOut()
                            signOut.invoke()
                        } else {
                            navigateToMenu.invoke(menuItem)
                        }
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

}
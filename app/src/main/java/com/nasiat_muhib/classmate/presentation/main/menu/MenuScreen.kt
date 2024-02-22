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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.navigation.MenuItem
import com.nasiat_muhib.classmate.navigation.Routes
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.auth.sign_in.SignInViewModel
import com.nasiat_muhib.classmate.presentation.main.ClassMateAppScreen
import com.nasiat_muhib.classmate.presentation.main.menu.components.MenuContent

@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = hiltViewModel(),
    navigateToTab: (TabItem) -> Unit,
    navigateToMenu: (MenuItem) -> Unit,
) {
    val uiState by menuViewModel.menuState.collectAsState()

    when(uiState) {
        ResponseState.Loading -> LoadingScreen()
        is ResponseState.Success -> {
            MenuContent(
                menuViewModel = menuViewModel,
                navigateToTab = { tabItem ->  navigateToTab.invoke(tabItem)},
                navigateToMenu = {menuItem -> navigateToMenu.invoke(menuItem) },
            )
        }
        is ResponseState.Failure -> TODO()
    }
}
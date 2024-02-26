package com.nasiat_muhib.classmate.presentation.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.ClassMateAppScreen
import com.nasiat_muhib.classmate.presentation.main.home.components.HomeScreenContent

@Composable
fun HomeScreen(navigateToTab: (TabItem) -> Unit) {

    HomeScreenContent(navigateToTab = navigateToTab)

}
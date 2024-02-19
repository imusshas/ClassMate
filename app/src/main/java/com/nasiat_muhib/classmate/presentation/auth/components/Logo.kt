package com.nasiat_muhib.classmate.presentation.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nasiat_muhib.classmate.R

@Composable
fun Logo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
        Image(painter = painterResource(id = R.drawable.classmate), contentDescription = null)
    }
}
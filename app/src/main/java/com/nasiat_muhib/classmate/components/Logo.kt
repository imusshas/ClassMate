package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.strings.APP_LOGO
import com.nasiat_muhib.classmate.strings.CLASSMATE_LOGO

@Composable
fun Logo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = APP_LOGO,
            modifier = Modifier.weight(0.75f)
        )

        Image(
            painter = painterResource(id = R.drawable.classmate),
            contentDescription = CLASSMATE_LOGO,
            modifier = Modifier.weight(0.2f)
        )
    }
}
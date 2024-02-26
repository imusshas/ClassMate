package com.nasiat_muhib.classmate.presentation.main.menu.profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.core.Constants.APP_LOGO_BACKGROUND
import com.nasiat_muhib.classmate.ui.theme.BackgroundRed

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfilePicture() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.app_logo_background),
            contentDescription = APP_LOGO_BACKGROUND,
            contentScale = ContentScale.FillBounds,
            alpha = 0.8f,
            modifier = Modifier.fillMaxSize()
        )

    }
}




package com.nasiat_muhib.classmate.presentation.main.menu.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nasiat_muhib.classmate.navigation.MenuItem
import com.nasiat_muhib.classmate.ui.theme.ButtonStyle
import com.nasiat_muhib.classmate.ui.theme.MediumRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun SignOutItem(menuItem: MenuItem, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace),
        shape = MediumRounded
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = menuItem.iconId), contentDescription = menuItem.title)
            Spacer(modifier = Modifier.width(SmallSpace))
            Text(text = menuItem.title, style = ButtonStyle)
        }
    }
}
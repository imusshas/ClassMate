package com.nasiat_muhib.classmate.presentation.main.menu.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserInfo(
    title: String,
    info: String,
    onInfoChange: (String) -> Unit,
    isEditable: Boolean,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Text(text = "$title:", modifier.weight(1f))
        TextField(
            value = info,
            onValueChange = onInfoChange,
            enabled = isEditable,
            modifier = modifier.weight(2.25f)
        )
    }
}
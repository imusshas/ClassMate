package com.nasiat_muhib.classmate.presentation.main.menu.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants

@Composable
fun UserPasswordInfo(
    title: String,
    info: String,
    onInfoChange: (String) -> Unit,
    isEditable: Boolean,
    modifier: Modifier = Modifier,
) {

    var isVisible by rememberSaveable { mutableStateOf(false) }

    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Text(text = "$title:", modifier.weight(1f))
        TextField(
            value = info,
            onValueChange = onInfoChange,
            trailingIcon = {
                IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(imageVector = if(isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = Constants.VISIBILITY_ICON)
                }
            },
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            enabled = isEditable,
            modifier = modifier.weight(2.25f),
        )
    }
}
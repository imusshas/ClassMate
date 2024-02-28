package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
fun NormalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    singleLine: Boolean = true
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        singleLine = singleLine,
        modifier = modifier
    )
}
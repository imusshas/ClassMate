package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nasiat_muhib.classmate.core.Constants.EMAIL_LABEL

@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    imeAction: ImeAction,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(text = EMAIL_LABEL) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = imeAction),
        singleLine = true,
        modifier = modifier
    )
}
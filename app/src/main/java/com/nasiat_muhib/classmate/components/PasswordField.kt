package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nasiat_muhib.classmate.core.Constants.PASSWORD_LABEL

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    imeAction: ImeAction,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = PASSWORD_LABEL) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = imeAction),
        modifier = modifier
    )
}
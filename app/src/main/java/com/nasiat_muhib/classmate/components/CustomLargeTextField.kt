package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MaximumHeight

@Composable
fun CustomLargeTextField(
    placeholderValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    value: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(),
//    shape: Shape = MediumRounded,
    readOnly: Boolean = false,
    errorMessage: String? = null
) {
    val textValue = remember { mutableStateOf(value) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(ExtraExtraSmallSpace)
    ) {
        TextField(
            value = textValue.value,
            onValueChange = {
                textValue.value = it
                onValueChange(it)
            },
            placeholder = { Text(text = placeholderValue) },
            modifier = Modifier
                .fillMaxWidth()
                .height(MaximumHeight),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
//            shape = shape,
            readOnly = readOnly,
            isError = errorMessage != null,
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview (showSystemUi = true)
@Composable
fun CustomTextFieldPreview() {
    CustomLargeTextField(placeholderValue = "Update", onValueChange = {})
}
package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.nasiat_muhib.classmate.ui.theme.Dark
import com.nasiat_muhib.classmate.ui.theme.DarkText
import com.nasiat_muhib.classmate.ui.theme.MediumRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.Light
import com.nasiat_muhib.classmate.ui.theme.LightText
import com.nasiat_muhib.classmate.ui.theme.PrimaryRed
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace

@Composable
fun CustomOutlinedFieldWithPlaceholder(
    labelValue: String,
    placeholderValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    value: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions(),
    singeLine: Boolean = true,
    maxLines: Int = 1,
    shape: Shape = MediumRounded,
    readOnly: Boolean = false,
    startPadding: Dp = MediumSpace,
    endPadding: Dp = MediumSpace,
    topPadding: Dp = ZeroSpace,
    bottomPadding: Dp = ZeroSpace,
    errorMessage: String? = null
) {
    val textValue = remember { mutableStateOf(value) }
    val textColor = if (isSystemInDarkTheme()) DarkText else LightText

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = startPadding, top = topPadding, end = endPadding, bottom = bottomPadding
            ),
        verticalArrangement = Arrangement.spacedBy(ExtraExtraSmallSpace)
    ) {
        OutlinedTextField(
            value = textValue.value,
            onValueChange = {
                textValue.value = it
                onValueChange(it)
            },
            label = {
                Text(text = labelValue, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    },
            placeholder = {
                Text(text = placeholderValue, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                          },
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singeLine,
            maxLines = maxLines,
            shape = shape,
            readOnly = readOnly,
            isError = errorMessage != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                focusedBorderColor = textColor,
                focusedLabelColor = textColor,
                focusedPlaceholderColor = textColor,
                focusedSupportingTextColor = textColor,
                focusedPrefixColor = textColor,
                focusedSuffixColor = textColor,
            )
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
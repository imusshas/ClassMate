package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace

@Composable
fun CustomOutlinedField(
    labelValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    singeLine: Boolean = true,
    maxLines: Int = 1,
    shape: Shape = MediumRounded,
    readOnly: Boolean = false,
    horizontalPadding: Dp = MediumSpace,
    verticalPadding: Dp = ZeroSpace
) {
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onValueChange(it)
        },
        label = { Text(text = labelValue) },
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            ),
        keyboardOptions = keyboardOptions,
        singleLine = singeLine,
        maxLines = maxLines,
        shape = shape,
        readOnly = readOnly
    )
}
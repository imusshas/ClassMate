package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.ui.theme.ButtonStyle
import com.nasiat_muhib.classmate.ui.theme.MediumRounded

@Composable
fun CustomElevatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MediumRounded,
    enabled: Boolean = true
) {

    ElevatedButton(
        onClick = onClick,
        shape = shape,
        modifier = modifier.sizeIn(minWidth = 96.dp, minHeight = 48.dp),
        enabled = enabled
    ) {
        Text(
            text = text, style = ButtonStyle,
            textAlign = TextAlign.Center
        )
    }
}
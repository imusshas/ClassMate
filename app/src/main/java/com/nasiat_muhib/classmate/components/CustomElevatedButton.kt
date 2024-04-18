package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.ui.theme.ButtonStyle
import com.nasiat_muhib.classmate.ui.theme.Dark
import com.nasiat_muhib.classmate.ui.theme.DarkText
import com.nasiat_muhib.classmate.ui.theme.LightText
import com.nasiat_muhib.classmate.ui.theme.MediumRounded
import com.nasiat_muhib.classmate.ui.theme.PrimaryRed

@Composable
fun CustomElevatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = if (isSystemInDarkTheme()) DarkText else LightText,
    containerColor: Color = PrimaryRed,
    shape: Shape = MediumRounded,
    enabled: Boolean = true,
) {

    ElevatedButton(
        onClick = onClick,
        shape = shape,
        modifier = modifier.sizeIn(minWidth = 96.dp, minHeight = 48.dp),
        enabled = enabled,
        colors = ButtonDefaults.elevatedButtonColors(
            contentColor = contentColor,
            containerColor = containerColor
        )
    ) {
        Text(
            text = text, style = ButtonStyle,
            textAlign = TextAlign.Center
        )
    }
}
package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun CustomClickableText(
    text: String,
    onClick: (Int) -> Unit,
) {

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.W200,
                textDecoration = TextDecoration.Underline,
                color = Color.Unspecified,
            )
        ) {
            pushStringAnnotation(text, text)
            append(text)
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = onClick,
    )
}
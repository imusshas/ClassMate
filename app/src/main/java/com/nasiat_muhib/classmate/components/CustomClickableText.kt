package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.nasiat_muhib.classmate.ui.theme.ClickableTextStyle
import com.nasiat_muhib.classmate.ui.theme.DarkText
import com.nasiat_muhib.classmate.ui.theme.LightText

@Composable
fun CustomClickableText(
    text: String,
    onClick: (Int) -> Unit,
    style: SpanStyle = ClickableTextStyle
) {

    val color = if (isSystemInDarkTheme()) DarkText else LightText

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = style.copy(color = color)
        ) {
            pushStringAnnotation(text, text)
            append(text)
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = { onClick(it) },
    )
}
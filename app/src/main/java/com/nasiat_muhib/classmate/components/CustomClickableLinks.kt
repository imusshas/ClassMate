package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.nasiat_muhib.classmate.ui.theme.ClickableTextStyle

@Composable
fun CustomClickableLinks(
    text: String,
    onClick: (Int) -> Unit,
    style: SpanStyle = ClickableTextStyle
) {

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = style
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
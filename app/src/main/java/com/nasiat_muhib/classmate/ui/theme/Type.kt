package com.nasiat_muhib.classmate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
titleLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
),
labelSmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)
*/
)

val ButtonStyle = TextStyle(
    fontWeight = FontWeight.Bold
)

val TitleStyle = TextStyle(
    fontWeight = FontWeight.Bold
)

val PickerStyle = TextStyle(
    fontSize = 32.sp,
    textAlign = TextAlign.Center,
)

val SmallPickerStyle = TextStyle(
    fontSize = 18.sp,
    textAlign = TextAlign.Center,
)

val ClickableTextStyle = SpanStyle(
    fontWeight = FontWeight.Bold,
)

val ClickableLinkStyle = SpanStyle(
    fontWeight = FontWeight.Bold,
    textDecoration = TextDecoration.Underline,
    color = Color.Blue
)

val SomeStyle = TextStyle(
    textAlign = TextAlign.Center,
    fontSize = 14.sp,
    fontWeight = FontWeight.Medium
)
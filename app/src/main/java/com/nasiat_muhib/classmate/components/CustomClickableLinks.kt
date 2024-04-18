package com.nasiat_muhib.classmate.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.nasiat_muhib.classmate.ui.theme.ClickableLinkStyle
import com.nasiat_muhib.classmate.ui.theme.ClickableTextStyle
import com.nasiat_muhib.classmate.ui.theme.PrimaryRed

@Composable
fun CustomClickableLinks(
    text: String,
    annotation: String,
    style: SpanStyle = ClickableLinkStyle
) {

    val context = LocalContext.current

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = style.copy(color = PrimaryRed)
        ) {
            pushStringAnnotation(
                tag = text,
                annotation = annotation)
            append(text)
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = {  offSet ->
                  annotatedText.getStringAnnotations(offSet, offSet)
                      .firstOrNull()?.let { annotation ->
                          if (annotation.tag == text) {
                              Uri.parse(annotation.item).also {uri ->
                                  context.startActivity(
                                      Intent(Intent.ACTION_VIEW, uri)
                                  )
                              }
                          }
                      }
        },
    )
}
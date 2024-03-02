package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nasiat_muhib.classmate.ui.theme.ClickableTextStyle
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.ExtraLargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun CustomDialog(
    maxWidth: Float = 0.9f,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(maxWidth),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                content()
                Spacer(modifier = Modifier.height(ExtraExtraLargeSpace))
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MediumSpace),
                    horizontalArrangement = Arrangement.End
                ){
                    CustomClickableText(text = "Cancel", onClick = {}, style = ClickableTextStyle.copy(
                        textDecoration = TextDecoration.None,
                        fontWeight = FontWeight.Normal
                    ))
                    Spacer(modifier = Modifier.width(SmallSpace))
                    CustomClickableText(text = "Ok", onClick = {}, style = ClickableTextStyle.copy(
                        textDecoration = TextDecoration.None,
                        fontWeight = FontWeight.Normal
                    ))
                }
                Spacer(modifier = Modifier.height(ExtraLargeSpace))
            }
        }
    }



}

@Preview(showBackground = true)
@Composable
fun CustomDialogPreview() {
    CustomDialog(
        content = {},
        onDismissRequest = {}
    )
}
package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomDialog(
    hasCancelButton: Boolean = true,
    onDismissRequest: () -> Unit,
    fraction: Float = 0.8f,
    upSpacerHeight: Dp = 32.dp,
    downSpacerHeight: Dp = 32.dp,
    content: @Composable () -> Unit
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = hasCancelButton,
            decorFitsSystemWindows = false
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(15.dp))
                .shadow(15.dp, RoundedCornerShape(15.dp))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(upSpacerHeight))
            content.invoke()
            Spacer(modifier = Modifier.height(downSpacerHeight))
        }
    }
}
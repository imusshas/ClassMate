package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants.DISCARD_BUTTON
import com.nasiat_muhib.classmate.core.Constants.POST_BUTTON
import com.nasiat_muhib.classmate.ui.theme.ButtonBoldStyle

@Composable
fun UpdateBox(
    update: String,
    onUpdateChange: (String) -> Unit,
    label: String,
    onDiscardClick: () -> Unit,
    onPostClick: () -> Unit,
) {
    CustomDialog(
        onDismissRequest = onDiscardClick,
        upSpacerHeight = 0.dp
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NormalTextField(
                value = update,
                onValueChange = onUpdateChange,
                placeholder = label,
                singleLine = false,
                imeAction = ImeAction.Done,
                modifier = Modifier.heightIn(min = 160.dp)
            )


            
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = DISCARD_BUTTON,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onDiscardClick.invoke() },
                    style = ButtonBoldStyle
                    )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = POST_BUTTON, color = MaterialTheme.colorScheme.primary, modifier = Modifier.clickable { onPostClick.invoke() }, style = ButtonBoldStyle)
            }
        }

    }
}
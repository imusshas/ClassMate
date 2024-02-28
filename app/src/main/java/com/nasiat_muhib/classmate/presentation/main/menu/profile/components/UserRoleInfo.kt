package com.nasiat_muhib.classmate.presentation.main.menu.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.AutoCompleteTextField

@Composable
fun UserRoleInfo(
    title: String,
    infoList: List<String>,
    info: String,
    onInfoChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Text(text = "$title:", modifier.weight(1f))
        AutoCompleteTextField(
            itemsList = infoList,
            selectedItem = info,
            onItemChange = onInfoChange,
            imeAction = imeAction,
            modifier = Modifier.weight(2.25f)
        )
    }
}
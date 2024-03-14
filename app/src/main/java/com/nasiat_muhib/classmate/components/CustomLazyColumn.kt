package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.ui.theme.MaximumHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun <T> CustomLazyColumn  (
    items: List<T>,
    content: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = Modifier
        .fillMaxWidth()
        .heightIn(max = MaximumHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SmallSpace)
    ) {
        items(
            items = items
        ) {
            content(it)
        }
    }
}
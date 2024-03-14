package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.ui.theme.MaximumHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun <T> CustomSwipeAbleLazyColumn (
    items: List<T>,
    key: (T) -> String,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    LazyColumn (
        modifier = Modifier.fillMaxWidth().height(350.dp),
        verticalArrangement = Arrangement.spacedBy(SmallSpace),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = items,
            key = {
                key(it)
            }
        ) {
            content(it)
        }
    }
}
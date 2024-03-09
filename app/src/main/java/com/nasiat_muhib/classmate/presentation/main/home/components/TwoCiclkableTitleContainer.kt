package com.nasiat_muhib.classmate.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SomeStyle

@Composable
fun TwoClickableTitleContainer(
    leftTitle: String,
    rightTitle: String,
) {

    val leftSelected = rememberSaveable { mutableStateOf(true) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace),
        shape = LargeRounded,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            contentColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row {
            CustomTitle(
                title = leftTitle,
                selected = leftSelected.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable {
                        leftSelected.value = true
                    }
            )
            CustomTitle(
                title = rightTitle,
                selected = !leftSelected.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable {
                        leftSelected.value = false
                    }
            )
        }
    }
}


@Composable
private fun CustomTitle(
    title: String,
    selected: Boolean,
    modifier: Modifier
) {
    Row (
        modifier = modifier
            .background(if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.primaryContainer),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = SomeStyle,
            color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.primaryContainer
        )
    }
}
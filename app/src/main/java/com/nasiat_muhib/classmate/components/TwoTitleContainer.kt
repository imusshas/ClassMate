package com.nasiat_muhib.classmate.components

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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nasiat_muhib.classmate.ui.theme.LargeRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallSpace
import com.nasiat_muhib.classmate.ui.theme.SomeStyle

@Composable
fun TwoTitleContainer(
    leftTitle: String,
    rightTitle: String,
    leftClick: () -> Unit,
    rightClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLeftSelected = rememberSaveable { mutableStateOf(true) }


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
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(
                        if (isLeftSelected.value) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        }
                    )
                    .clickable {
                        isLeftSelected.value = true
                        leftClick()
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = leftTitle,
                    style = SomeStyle,
                    color = if (!isLeftSelected.value) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    }
                    )
            }
            VerticalDivider(color = Color.White, modifier = Modifier.height(NormalHeight))
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(
                        if (!isLeftSelected.value) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        }
                    )
                    .clickable {
                        isLeftSelected.value = false
                        rightClick()
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rightTitle,
                    style = SomeStyle,
                    color = if (isLeftSelected.value) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TwoTitleContainerPreview() {
    TwoTitleContainer(
        leftTitle = "Created Courses",
        rightTitle = "Pending Courses",
        leftClick = { /*TODO*/ },
        rightClick = { /*TODO*/ })
}
package com.nasiat_muhib.classmate.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants.ARROW_DROPDOWN_ICON
import java.util.Locale

@Composable
fun AutoCompleteField(
    itemsList: List<String>,
    selectedItem: String,
    onItemChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {
                expanded = true
                onItemChange.invoke(selectedItem)
            },
            readOnly = true,
            label = {
                Text(text = label)
            },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = ARROW_DROPDOWN_ICON
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        AnimatedVisibility(visible = expanded) {
            Card(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = 150.dp)
                        .fillMaxWidth()
                ) {
                    items(
                        items = itemsList.sorted()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(8.dp)
                                .clickable {
                                    onItemChange.invoke(it)
                                    expanded = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = it)
                        }
                    }

                }
            }
        }
    }
}
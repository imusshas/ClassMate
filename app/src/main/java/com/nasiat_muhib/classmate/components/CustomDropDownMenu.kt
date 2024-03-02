package com.nasiat_muhib.classmate.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MaximumHeight
import com.nasiat_muhib.classmate.ui.theme.MediumRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.NormalHeight
import com.nasiat_muhib.classmate.ui.theme.SmallBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    itemList: List<String>,
    selectedItem: String = itemList[0],
    onItemChange: (String) -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    var item by rememberSaveable {
        mutableStateOf(selectedItem)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumSpace),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier
                    .menuAnchor()
                    .clip(MediumRounded)
                    .fillMaxWidth()
                    .border(
                        SmallBorder,
                        color = if (isExpanded) MaterialTheme.colorScheme.primary else OutlinedTextFieldDefaults.colors().unfocusedTextColor,
                        MediumRounded
                    )
                    .padding(MediumSpace),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item, Modifier.weight(1f))
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)

            }

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = MaximumHeight)
            ) {
                itemList.forEach {
                    DropdownMenuItem(
                        text = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(text = it)
                                Spacer(modifier = Modifier.height(ExtraSmallSpace))
                                if (it != itemList[itemList.size - 1]) {
                                    HorizontalDivider()
                                }
                            }
                        },
                        onClick = {
                            isExpanded = false
                            item = it
                            onItemChange(it)
                        },
                        modifier = Modifier.height(NormalHeight),
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

    }
}
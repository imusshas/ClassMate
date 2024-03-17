package com.nasiat_muhib.classmate.presentation.main.menu.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import com.nasiat_muhib.classmate.components.CustomTextFieldWithOutLabel
import com.nasiat_muhib.classmate.core.Constants.CHANGEABLE_ROLES
import com.nasiat_muhib.classmate.strings.TEACHER
import com.nasiat_muhib.classmate.ui.theme.ExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.TitleStyle
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace

@Composable
fun RoleInfo(
    title: String,
    role: String,
    onRoleChange: (String) -> Unit,
    readOnly: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MediumSpace),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$title:", style = TitleStyle, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(ExtraSmallSpace))
        if (role == TEACHER) {
            CustomTextFieldWithOutLabel(
                value = role,
                onValueChange = { value ->
                },
                startPadding = ZeroSpace,
                endPadding = ZeroSpace,
                shape = RectangleShape,
                readOnly = true,
                modifier = Modifier.weight(3.25f)
            )
        } else {
            RoleDropDownMenu(
                itemsList = CHANGEABLE_ROLES,
                selectedItem = role,
                onItemChange = { value ->
                    onRoleChange(value)
                },
                imeAction = ImeAction.Next,
                modifier = Modifier.weight(3.25f),
                enabled = readOnly
            )
        }
    }
}
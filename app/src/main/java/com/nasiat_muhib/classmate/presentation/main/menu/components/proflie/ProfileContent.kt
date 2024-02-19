package com.nasiat_muhib.classmate.presentation.main.menu.components.proflie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants.DEPARTMENT
import com.nasiat_muhib.classmate.core.Constants.FIRST_NAME
import com.nasiat_muhib.classmate.core.Constants.LAST_NAME
import com.nasiat_muhib.classmate.core.Constants.ROLE

@Composable
fun ProfileContent() {

    var isEditable by rememberSaveable { mutableStateOf(false) }
    
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        ProfilePicture()

        Column {
            UserInfo(title = FIRST_NAME, info = "Firstname", onInfoChange = {}, isEditable = isEditable)
            UserInfo(title = LAST_NAME, info = "Lastname", onInfoChange = {}, isEditable = isEditable)
            UserInfo(title = ROLE, info = "Role", onInfoChange = {}, isEditable = isEditable)
            UserInfo(title = DEPARTMENT, info = "Department", onInfoChange = {}, isEditable = isEditable)
        }
    }
}
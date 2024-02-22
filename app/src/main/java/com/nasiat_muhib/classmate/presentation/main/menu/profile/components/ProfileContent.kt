package com.nasiat_muhib.classmate.presentation.main.menu.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.core.Constants.BLOOD_GROUP_TITLE
import com.nasiat_muhib.classmate.core.Constants.DEPARTMENT_TITLE
import com.nasiat_muhib.classmate.core.Constants.DONE_BUTTON
import com.nasiat_muhib.classmate.core.Constants.EDIT_PROFILE_BUTTON
import com.nasiat_muhib.classmate.core.Constants.EMAIL_TITLE
import com.nasiat_muhib.classmate.core.Constants.FIRST_NAME_TITLE
import com.nasiat_muhib.classmate.core.Constants.GO_BACK_BUTTON
import com.nasiat_muhib.classmate.core.Constants.LAST_NAME_TITLE
import com.nasiat_muhib.classmate.core.Constants.PASSWORD_TITLE
import com.nasiat_muhib.classmate.core.Constants.PHONE_NO_TITLE
import com.nasiat_muhib.classmate.core.Constants.ROLE_TITLE
import com.nasiat_muhib.classmate.core.Constants.SESSION_TITLE
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.presentation.main.menu.profile.ProfileViewModel

@Composable
fun ProfileContent(
    profileViewModel: ProfileViewModel,
    user: User,
    navigateBackToMenuScreen: () -> Unit
) {

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var role by rememberSaveable { mutableStateOf("") }
    var department by rememberSaveable { mutableStateOf("") }
    var session by rememberSaveable { mutableStateOf("") }
    var bloodGroup by rememberSaveable { mutableStateOf("") }
    var phoneNo by rememberSaveable { mutableStateOf("") }

    var isEditable by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
//        ProfilePicture()

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UserInfo(
                title = FIRST_NAME_TITLE,
                info = if(isEditable) firstName else user.firstName,
                onInfoChange = {firstName = it},
                isEditable = isEditable
            )
            UserInfo(
                title = LAST_NAME_TITLE,
                info = if(isEditable) lastName else user.lastName,
                onInfoChange = {lastName = it},
                isEditable = isEditable
            )
            /*TODO: Implement a dropdown box*/
            UserInfo(title = ROLE_TITLE, info = user.role, onInfoChange = {}, isEditable = isEditable)
            UserInfo(
                title = DEPARTMENT_TITLE,
                info = if(isEditable) department else user.department,
                onInfoChange = {department = it},
                isEditable = isEditable
            )
            UserInfo(
                title = SESSION_TITLE,
                info = if(isEditable) session else user.session,
                onInfoChange = {session = it},
                isEditable = isEditable
            )
            UserInfo(
                title = BLOOD_GROUP_TITLE,
                info = if(isEditable) bloodGroup else user.bloodGroup,
                onInfoChange = {bloodGroup = it},
                isEditable = isEditable
            )
            UserInfo(
                title = PHONE_NO_TITLE,
                info = if(isEditable) phoneNo else user.phoneNo,
                onInfoChange = {phoneNo = it},
                isEditable = isEditable
            )
            UserInfo(title = EMAIL_TITLE, info = user.email, onInfoChange = {}, isEditable = false)
            UserInfo(
                title = PASSWORD_TITLE,
                info = user.password,
                onInfoChange = {},
                isEditable = false
            )
        }

        Row (modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    if(isEditable) {
                        val userData = User(
                            firstName = firstName,
                            lastName = lastName,
                            department = department,
                            session = session,
                            bloodGroup = bloodGroup,
                            phoneNo = phoneNo,
                        )
                        profileViewModel.updateUser(user.email, userData)
                    }
                    isEditable = !isEditable
                }) {
                    Text(text = if (isEditable) DONE_BUTTON else EDIT_PROFILE_BUTTON)
                }
            }

            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(onClick = navigateBackToMenuScreen) {
                    Text(text = GO_BACK_BUTTON)
                }
            }
        }
    }
}
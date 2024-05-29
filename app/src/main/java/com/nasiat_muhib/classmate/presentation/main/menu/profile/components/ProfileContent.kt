package com.nasiat_muhib.classmate.presentation.main.menu.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.EditProfileUIEvent
import com.nasiat_muhib.classmate.presentation.main.menu.profile.ProfileViewModel
import com.nasiat_muhib.classmate.ui.theme.LargeSpace
import com.nasiat_muhib.classmate.ui.theme.MediumRounded
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun ProfileContent(
    profileViewModel: ProfileViewModel,
    user: User,
    navigateBackToMenu: () -> Unit
) {

    val editUserState by profileViewModel.editUserState.collectAsState()
    val editState by profileViewModel.editProfileButtonState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SmallSpace)
    ) {
        ProfileContentTopBar(
            onBackIconClick = navigateBackToMenu
        )
        Spacer(modifier = Modifier.height(MediumSpace))
        // FirstName
        UserInfo(
            title = "First Name",
            value = user.firstName,
            onValueChange = { firstName ->
                profileViewModel.onEditProfileEvent(EditProfileUIEvent.FirstNameChanged(firstName))

            },
            readOnly = !editState
        )
        // LastName
        UserInfo(
            title = "Last Name",
            value = user.lastName,
            onValueChange = { lastName ->
                profileViewModel.onEditProfileEvent(EditProfileUIEvent.LastNameChanged(lastName))
            },
            readOnly = !editState
        )

        // Role
        RoleInfo(
            title = "Role",
            role = user.role,
            onRoleChange = { role ->
                profileViewModel.onEditProfileEvent(EditProfileUIEvent.RoleChanged(role))
            },
            readOnly = !editState
        )

        // Department
        UserInfo(
            title = "Department",
            value = user.department,
            onValueChange = { department -> },
            readOnly = true
        )

        // Session
        UserInfo(
            title = "Session",
            value = user.session,
            onValueChange = { session ->
                profileViewModel.onEditProfileEvent(EditProfileUIEvent.SessionChanged(session))
            },
            errorMessage = editUserState.sessionError,
            readOnly = !editState
        )

        // Blood Group
        UserInfo(
            title = "Blood Group",
            value = user.bloodGroup,
            onValueChange = { bloodGroup ->
                profileViewModel.onEditProfileEvent(EditProfileUIEvent.BloodGroupChanged(bloodGroup))
            },
            errorMessage = editUserState.bloodGroupError,
            readOnly = !editState
        )

        // Phone No
        UserInfo(
            title = "Phone No",
            value = user.phoneNo,
            onValueChange = { phoneNo ->
                profileViewModel.onEditProfileEvent(EditProfileUIEvent.PhoneNoChanged(phoneNo))
            },
            errorMessage = editUserState.phoneNoError,
            readOnly = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        // Email
        UserInfo(
            title = "Email",
            value = user.email,
            onValueChange = { email -> },
            readOnly = true
        )

        Spacer(modifier = Modifier.height(LargeSpace))
        Button(
            onClick = {
                if (editState) {
                    profileViewModel.onEditProfileEvent(EditProfileUIEvent.DoneButtonClicked)
                } else {
                    profileViewModel.onEditProfileEvent(EditProfileUIEvent.EditButtonClicked)
                }
            },
            shape = MediumRounded
        ) {
            Text(text = if (editState) "Done" else "Edit")
        }
    }

}
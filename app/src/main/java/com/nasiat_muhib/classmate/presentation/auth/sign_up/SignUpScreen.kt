package com.nasiat_muhib.classmate.presentation.auth.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.CustomDropDownMenu
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.CustomPasswordField
import com.nasiat_muhib.classmate.components.Logo
import com.nasiat_muhib.classmate.core.Constants.ROLES
import com.nasiat_muhib.classmate.domain.event.SignUpUIEvent
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import com.nasiat_muhib.classmate.navigation.SystemBackButtonHandler
import com.nasiat_muhib.classmate.strings.ALREADY_A_USER_HARDCODED
import com.nasiat_muhib.classmate.strings.EMAIL_LABEL
import com.nasiat_muhib.classmate.strings.FIRST_NAME_LABEL
import com.nasiat_muhib.classmate.strings.LAST_NAME_LABEL
import com.nasiat_muhib.classmate.strings.PASSWORD_LABEL
import com.nasiat_muhib.classmate.strings.ROLE_HARDCODED
import com.nasiat_muhib.classmate.strings.SIGN_IN_BUTTON
import com.nasiat_muhib.classmate.strings.SIGN_UP_BUTTON
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraSmallSpace
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {

    val signUpState = signUpViewModel.signUpState.collectAsState()
    val signUpInProgress = signUpViewModel.signUpInProgress.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Logo()

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                CustomOutlinedField(
                    labelValue = FIRST_NAME_LABEL,
                    onValueChange = { firstName ->
                        signUpViewModel.onEvent(SignUpUIEvent.FirstNameChanged(firstName))
                    },
                    errorMessage = signUpState.value.firstNameError,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = ZeroSpace)
                )
                CustomOutlinedField(
                    labelValue = LAST_NAME_LABEL,
                    onValueChange = { lastName ->
                        signUpViewModel.onEvent(SignUpUIEvent.LastNameChanged(lastName))
                    },
                    errorMessage = signUpState.value.lastNameError,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = ZeroSpace)
                )
            }


            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = ROLE_HARDCODED, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = MediumSpace)
                )
                CustomDropDownMenu(
                    itemList = ROLES,
                    onItemChange = { role ->
                        signUpViewModel.onEvent(SignUpUIEvent.RoleChanged(role))
                    }
                )
            }
            CustomOutlinedField(
                labelValue = EMAIL_LABEL,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { email ->
                    signUpViewModel.onEvent(SignUpUIEvent.EmailChanged(email))
                },
                errorMessage = signUpState.value.emailError
            )
            CustomPasswordField(labelValue = PASSWORD_LABEL, onPasswordChange = { password ->
                signUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(password))
            }, errorMessage = signUpState.value.passwordError)

            CustomElevatedButton(text = SIGN_UP_BUTTON, onClick = {
                /*TODO*/
                signUpViewModel.onEvent(SignUpUIEvent.SignUpButtonClicked)
            })
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = ALREADY_A_USER_HARDCODED)
            CustomElevatedButton(text = SIGN_IN_BUTTON, onClick = {
                ClassMateAppRouter.navigateTo(Screen.SignInScreen)
            })

        }
    }


    if (signUpInProgress.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }


    SystemBackButtonHandler {
        ClassMateAppRouter.navigateTo(Screen.SignInScreen)
    }
}
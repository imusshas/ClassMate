package com.nasiat_muhib.classmate.presentation.auth.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nasiat_muhib.classmate.components.CustomDropDownMenu
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.CustomPasswordField
import com.nasiat_muhib.classmate.components.Logo
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.domain.event.SignUpUIEvent
import com.nasiat_muhib.classmate.strings.ALREADY_A_USER_HARDCODED
import com.nasiat_muhib.classmate.strings.COURSE_DEPARTMENT_LABEL
import com.nasiat_muhib.classmate.strings.EMAIL_LABEL
import com.nasiat_muhib.classmate.strings.FIRST_NAME_LABEL
import com.nasiat_muhib.classmate.strings.LAST_NAME_LABEL
import com.nasiat_muhib.classmate.strings.PASSWORD_LABEL
import com.nasiat_muhib.classmate.strings.PHONE_NO_LABEL
import com.nasiat_muhib.classmate.strings.ROLE_HARDCODED
import com.nasiat_muhib.classmate.strings.SIGN_IN_BUTTON
import com.nasiat_muhib.classmate.strings.SIGN_UP_BUTTON
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.SmallSpace

@Composable
fun SignUpScreenContent(
    signUpViewModel: SignUpViewModel,
    navigateBackToSignInScreen: () -> Unit,
) {

    val signUpUIState = signUpViewModel.signUpUIState.collectAsState()
    val otpDialogState = signUpViewModel.otpScreenState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Logo()

        if (otpDialogState.value) {
            VerifyOTPScreen(signUpViewModel = signUpViewModel, signUpUIState = signUpUIState.value)
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(SmallSpace)
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
                        errorMessage = signUpUIState.value.firstNameError,
                        modifier = Modifier
                            .weight(1f)
                    )
                    CustomOutlinedField(
                        labelValue = LAST_NAME_LABEL,
                        onValueChange = { lastName ->
                            signUpViewModel.onEvent(SignUpUIEvent.LastNameChanged(lastName))
                        },
                        errorMessage = signUpUIState.value.lastNameError,
                        modifier = Modifier
                            .weight(1f)
                    )
                }


                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = ROLE_HARDCODED, modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MediumSpace)
                    )
                    CustomDropDownMenu(
                        itemList = Constants.ROLES,
                        onItemChange = { role ->
                            signUpViewModel.onEvent(SignUpUIEvent.RoleChanged(role))
                        }
                    )
                }

                CustomOutlinedField(
                    labelValue = COURSE_DEPARTMENT_LABEL,
                    onValueChange = { department ->
                        signUpViewModel.onEvent(SignUpUIEvent.DepartmentChanged(department))
                    },
                    errorMessage = signUpUIState.value.departmentError
                )

                CustomOutlinedField(
                    labelValue = PHONE_NO_LABEL,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = {phoneNo ->
                        signUpViewModel.onEvent(SignUpUIEvent.PhoneNoChanged(phoneNo))
                    }, errorMessage = signUpUIState.value.phoneNoError
                )

                CustomOutlinedField(
                    labelValue = EMAIL_LABEL,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = { email ->
                        signUpViewModel.onEvent(SignUpUIEvent.EmailChanged(email))
                    },
                    errorMessage = signUpUIState.value.emailError
                )
                CustomPasswordField(labelValue = PASSWORD_LABEL, onPasswordChange = { password ->
                    signUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(password))
                }, errorMessage = signUpUIState.value.passwordError)

                CustomElevatedButton(text = SIGN_UP_BUTTON, onClick = {
                    signUpViewModel.onEvent(SignUpUIEvent.SignUpButtonClicked)
                })
            }


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(SmallSpace)
            ) {
                Text(text = ALREADY_A_USER_HARDCODED)
                CustomElevatedButton(text = SIGN_IN_BUTTON, onClick = navigateBackToSignInScreen)
            }
        }
    }
}
package com.nasiat_muhib.classmate.presentation.auth.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.CustomClickableText
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.CustomPasswordField
import com.nasiat_muhib.classmate.components.Logo
import com.nasiat_muhib.classmate.data.viewmodel.AuthViewModel
import com.nasiat_muhib.classmate.domain.event.UIEvent
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import com.nasiat_muhib.classmate.strings.EMAIL_LABEL
import com.nasiat_muhib.classmate.strings.FORGOT_PASSWORD_BUTTON
import com.nasiat_muhib.classmate.strings.NEW_IN_CLASSMATE_HARDCODED
import com.nasiat_muhib.classmate.strings.PASSWORD_LABEL
import com.nasiat_muhib.classmate.strings.SIGN_IN_BUTTON
import com.nasiat_muhib.classmate.strings.SIGN_UP_BUTTON

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val signUpState = authViewModel.signUpState.collectAsState()

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
            CustomOutlinedField(
                labelValue = EMAIL_LABEL,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { email ->
                    authViewModel.onEvent(UIEvent.EmailChanged(email))
                },
                errorStatus = signUpState.value.emailError
            )
            CustomPasswordField(labelValue = PASSWORD_LABEL, onPasswordChange = { password ->
                authViewModel.onEvent(UIEvent.PasswordChanged(password))
            }, errorStatus = signUpState.value.passwordError)

            CustomElevatedButton(text = SIGN_IN_BUTTON, onClick = { /*TODO*/ })
            CustomClickableText(text = FORGOT_PASSWORD_BUTTON, onClick = {
                ClassMateAppRouter.navigateTo(Screen.ForgotPasswordScreen)
            })
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = NEW_IN_CLASSMATE_HARDCODED)
            CustomElevatedButton(
                text = SIGN_UP_BUTTON,
                onClick = {
                    ClassMateAppRouter.navigateTo(Screen.SignUpScreen)
                }
            )
        }
    }
}
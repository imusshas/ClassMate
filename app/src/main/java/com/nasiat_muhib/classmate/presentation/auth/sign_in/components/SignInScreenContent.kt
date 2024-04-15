package com.nasiat_muhib.classmate.presentation.auth.sign_in.components

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
import com.nasiat_muhib.classmate.components.CustomClickableText
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.CustomPasswordField
import com.nasiat_muhib.classmate.components.Logo
import com.nasiat_muhib.classmate.domain.event.SignInUIEvent
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.presentation.auth.sign_in.SignInViewModel
import com.nasiat_muhib.classmate.strings.EMAIL_LABEL
import com.nasiat_muhib.classmate.strings.FORGOT_PASSWORD_BUTTON
import com.nasiat_muhib.classmate.strings.NEW_IN_CLASSMATE_HARDCODED
import com.nasiat_muhib.classmate.strings.PASSWORD_LABEL
import com.nasiat_muhib.classmate.strings.SIGN_IN_BUTTON
import com.nasiat_muhib.classmate.strings.SIGN_UP_BUTTON

@Composable
fun SignInScreenContent(
    signInViewModel: SignInViewModel,
    navigateToHomeScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    error: String? = null
) {

    val signInUIState = signInViewModel.signInUIState.collectAsState()
    val signInDataState = signInViewModel.signInDataState.collectAsState()

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
                    signInViewModel.onEvent(
                        SignInUIEvent.EmailChanged(email)
                    )
                },
                errorMessage = if (error != null) "" else signInUIState.value.emailError
            )
            CustomPasswordField(labelValue = PASSWORD_LABEL, onPasswordChange = { password ->
                signInViewModel.onEvent(SignInUIEvent.PasswordChanged(password))
            }, errorMessage = if (error == null) signInUIState.value.passwordError else "Invalid Email or Password")

            CustomElevatedButton(text = SIGN_IN_BUTTON, onClick = {
                signInViewModel.onEvent(SignInUIEvent.SignInButtonClicked)
                if (signInDataState.value == DataState.Success(true)) {
                    navigateToHomeScreen()
                }
            })
            CustomClickableText(text = FORGOT_PASSWORD_BUTTON, onClick = {
                navigateToForgotPasswordScreen()
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
                onClick = navigateToSignUpScreen
            )
        }
    }
}
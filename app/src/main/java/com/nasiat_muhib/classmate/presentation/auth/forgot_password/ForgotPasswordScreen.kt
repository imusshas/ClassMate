package com.nasiat_muhib.classmate.presentation.auth.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nasiat_muhib.classmate.components.CustomElevatedButton
import com.nasiat_muhib.classmate.components.CustomOutlinedField
import com.nasiat_muhib.classmate.components.Logo
import com.nasiat_muhib.classmate.domain.event.ForgotPasswordUIEvent
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.EMAIL_LABEL
import com.nasiat_muhib.classmate.strings.RESET_PASSWORD_BUTTON
import com.nasiat_muhib.classmate.ui.theme.ExtraExtraLargeSpace

@Composable
fun ForgotPasswordScreen(
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateToSignInScreen: () -> Unit,
) {

    val forgotPasswordUIState = forgotPasswordViewModel.forgotPasswordUIState.collectAsState()
    val forgotPasswordDataState = forgotPasswordViewModel.forgotPasswordDataState.collectAsState()

    val localFocusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()
        Spacer(modifier = Modifier.height(ExtraExtraLargeSpace))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomOutlinedField(
                labelValue = EMAIL_LABEL,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                onValueChange = { email ->
                    forgotPasswordViewModel.onEvent(ForgotPasswordUIEvent.EmailChanged(email))
                },
                keyboardActions = KeyboardActions {
                    localFocusManager.clearFocus()
                },
                errorMessage = forgotPasswordUIState.value.emailError
            )
            CustomElevatedButton(text = RESET_PASSWORD_BUTTON, onClick = {
                forgotPasswordViewModel.onEvent(ForgotPasswordUIEvent.ResetPasswordButtonClick)
                if (forgotPasswordDataState.value == DataState.Success(true)) {
                        navigateToSignInScreen()
                }
            })
        }
    }
}
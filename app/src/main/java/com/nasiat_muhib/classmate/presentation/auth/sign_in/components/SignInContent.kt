package com.nasiat_muhib.classmate.presentation.auth.sign_in.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.EmailField
import com.nasiat_muhib.classmate.components.NormalField
import com.nasiat_muhib.classmate.components.PasswordField
import com.nasiat_muhib.classmate.core.Constants.FORGOT_PASSWORD
import com.nasiat_muhib.classmate.core.Constants.NEW_IN_CLASSMATE
import com.nasiat_muhib.classmate.core.Constants.SIGN_IN_BUTTON
import com.nasiat_muhib.classmate.core.Constants.SIGN_UP_BUTTON
import com.nasiat_muhib.classmate.presentation.auth.components.Logo

@Composable
fun SignInContent(
    signIn: (String, String) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen:() -> Unit
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {

        Logo()

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EmailField(email = email, onEmailChange = { email = it }, imeAction = ImeAction.Next)
            PasswordField(
                password = password,
                onPasswordChange = { password = it },
                imeAction = ImeAction.Done
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedButton(onClick = {
                signIn.invoke(email, password)
                navigateToHomeScreen.invoke()
            }) {
                Text(text = SIGN_IN_BUTTON)
            }

            TextButton(onClick = navigateToForgotPasswordScreen) {
                Text(text = FORGOT_PASSWORD)
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = NEW_IN_CLASSMATE)
            OutlinedButton(onClick = navigateToSignUpScreen) {
                Text(text = SIGN_UP_BUTTON)
            }

        }

    }
}
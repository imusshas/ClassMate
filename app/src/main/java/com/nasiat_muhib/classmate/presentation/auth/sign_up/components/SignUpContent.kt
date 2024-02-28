package com.nasiat_muhib.classmate.presentation.auth.sign_up.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.AutoCompleteField
import com.nasiat_muhib.classmate.components.EmailField
import com.nasiat_muhib.classmate.components.NormalField
import com.nasiat_muhib.classmate.components.PasswordField
import com.nasiat_muhib.classmate.core.Constants.ALREADY_USER
import com.nasiat_muhib.classmate.core.Constants.CLASS_REPRESENTATIVE
import com.nasiat_muhib.classmate.core.Constants.FIRSTNAME_LABEL
import com.nasiat_muhib.classmate.core.Constants.LASTNAME_LABEL
import com.nasiat_muhib.classmate.core.Constants.ROLE_LABEL
import com.nasiat_muhib.classmate.core.Constants.SIGN_IN_BUTTON
import com.nasiat_muhib.classmate.core.Constants.SIGN_UP_BUTTON
import com.nasiat_muhib.classmate.core.Constants.STUDENT
import com.nasiat_muhib.classmate.core.Constants.TAG
import com.nasiat_muhib.classmate.core.Constants.TEACHER
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.presentation.auth.components.Logo
import com.nasiat_muhib.classmate.ui.theme.ButtonBoldStyle
import com.nasiat_muhib.classmate.ui.theme.MediumButtonShape

@Composable
fun SignUpContent(
    signUp: (String, String, User) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToSignInScreen: () -> Unit
) {
    val roles = listOf(TEACHER, CLASS_REPRESENTATIVE, STUDENT)

    var role by rememberSaveable { mutableStateOf(roles[0]) }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {

        Logo()

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                NormalField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = FIRSTNAME_LABEL,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.weight(1f)
                )

                NormalField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = LASTNAME_LABEL,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.weight(1f)
                )
            }

            AutoCompleteField(
                itemsList = roles,
                selectedItem = role,
                onItemChange = { role = it },
                label = ROLE_LABEL,
                imeAction = ImeAction.Next
            )

            EmailField(
                email = email,
                onEmailChange = { email = it },
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                password = password,
                onPasswordChange = { password = it },
                imeAction = ImeAction.Done,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedButton(
                onClick = {
                    val user = User(
                        email = email,
                        firstName = firstName,
                        lastName = lastName,
                        password = password,
                        role = role
                    )
                    signUp.invoke(email, password, user)
                    Log.d(TAG, "SignUpContent: $role")
                    navigateToHomeScreen.invoke()
                },
                shape = MediumButtonShape
            ) {
                Text(text = SIGN_UP_BUTTON, style = ButtonBoldStyle)
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = ALREADY_USER)
            OutlinedButton(onClick = navigateToSignInScreen, shape = MediumButtonShape) {
                Text(text = SIGN_IN_BUTTON, style = ButtonBoldStyle)
            }

        }

    }
}
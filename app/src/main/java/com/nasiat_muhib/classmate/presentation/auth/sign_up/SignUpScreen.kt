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
import com.nasiat_muhib.classmate.components.LoadingScreen
import com.nasiat_muhib.classmate.components.Logo
import com.nasiat_muhib.classmate.core.Constants.ROLES
import com.nasiat_muhib.classmate.domain.event.SignUpUIEvent
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.Screen
import com.nasiat_muhib.classmate.navigation.SystemBackButtonHandler
import com.nasiat_muhib.classmate.strings.ALREADY_A_USER_HARDCODED
import com.nasiat_muhib.classmate.strings.COURSE_DEPARTMENT_LABEL
import com.nasiat_muhib.classmate.strings.EMAIL_LABEL
import com.nasiat_muhib.classmate.strings.FIRST_NAME_LABEL
import com.nasiat_muhib.classmate.strings.LAST_NAME_LABEL
import com.nasiat_muhib.classmate.strings.PASSWORD_LABEL
import com.nasiat_muhib.classmate.strings.ROLE_HARDCODED
import com.nasiat_muhib.classmate.strings.SIGN_IN_BUTTON
import com.nasiat_muhib.classmate.strings.SIGN_UP_BUTTON
import com.nasiat_muhib.classmate.ui.theme.MediumSpace
import com.nasiat_muhib.classmate.ui.theme.ZeroSpace

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateBackToSignInScreen: () -> Unit,
) {
    val signUpDataState = signUpViewModel.signUpDataState.collectAsState()

    when (signUpDataState.value) {
        is DataState.Error -> {
            SignUpScreenContent(
                signUpViewModel = signUpViewModel,
                navigateToHomeScreen = navigateToHomeScreen,
                navigateBackToSignInScreen = navigateBackToSignInScreen,
                error = signUpDataState.value.error
            )
        }
        DataState.Loading -> {
            LoadingScreen()
        }
        is DataState.Success -> {
            SignUpScreenContent(
                signUpViewModel = signUpViewModel,
                navigateToHomeScreen = navigateToHomeScreen,
                navigateBackToSignInScreen = navigateBackToSignInScreen,
            )
        }
    }
}
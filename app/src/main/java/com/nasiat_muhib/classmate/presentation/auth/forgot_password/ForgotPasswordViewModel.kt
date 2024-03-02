package com.nasiat_muhib.classmate.presentation.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.domain.event.ForgotPasswordUIEvent
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.domain.rules.AuthValidator
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.domain.state.ForgotPasswordUIState
import com.nasiat_muhib.classmate.domain.state.SignInUIState
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepo: AuthenticationRepository
): ViewModel() {

    private val _forgotPasswordUIState = MutableStateFlow(ForgotPasswordUIState())
    val forgotPasswordUIState = _forgotPasswordUIState.asStateFlow()

    private val _forgotPasswordDataState = MutableStateFlow<DataState<Boolean>>(DataState.Success(true))
    val forgotPasswordDataState = _forgotPasswordDataState.asStateFlow()

    private val _allValidationPassed = MutableStateFlow(false)
    private val allValidationPassed = _allValidationPassed.asStateFlow()

    fun onEvent(event: ForgotPasswordUIEvent) {

        when (event) {
            is ForgotPasswordUIEvent.EmailChanged -> {
                _forgotPasswordUIState.value = _forgotPasswordUIState.value.copy(email = event.email)
            }
            ForgotPasswordUIEvent.ForgotPasswordButtonClick -> {
                resetPassword()
            }
        }
    }

    private fun resetPassword() = viewModelScope.launch (Dispatchers.IO) {
        validateSignInUIDataWithRules()
        if(allValidationPassed.value) {
            authRepo.resetPassword(forgotPasswordUIState.value.email).collectLatest {
                _forgotPasswordDataState.value = it
            }

            if (forgotPasswordDataState.value == DataState.Success(true)) {
                ClassMateAppRouter.navigateTo(Screen.SignInScreen)
            }
        }
    }

    private fun validateSignInUIDataWithRules() {
        val emailResult = AuthValidator.validateEmail(forgotPasswordUIState.value.email)


        _forgotPasswordUIState.value = _forgotPasswordUIState.value.copy(
            emailError = emailResult.message,
        )


        _allValidationPassed.value = emailResult.message == null
    }
}
package com.nasiat_muhib.classmate.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nasiat_muhib.classmate.domain.event.UIEvent
import com.nasiat_muhib.classmate.domain.rules.Validator
import com.nasiat_muhib.classmate.domain.state.SignUpUIState
import com.nasiat_muhib.classmate.strings.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(

) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpUIState())
    val signUpState = _signUpState.asStateFlow()

    fun onEvent(event: UIEvent) {

        validateDataWithRules()

        when (event) {
            is UIEvent.FirstNameChanged -> {
                _signUpState.value = _signUpState.value.copy(firstName = event.firstName)
                printState()
            }

            is UIEvent.LastNameChanged -> {
                _signUpState.value = _signUpState.value.copy(lastName = event.lastName)
                printState()
            }

            is UIEvent.EmailChanged -> {
                _signUpState.value = _signUpState.value.copy(email = event.email)
                printState()
            }

            is UIEvent.PasswordChanged -> {
                _signUpState.value = _signUpState.value.copy(password = event.password)
                printState()
            }

            UIEvent.SignUpButtonClicked ->  {
                signUp()
            }
        }
    }


    private fun printState() {
        Log.d(TAG, "printState: ${signUpState.value}")
    }

    private fun signUp() {
        Log.d(TAG, "signUp: ${signUpState.value}")

        validateDataWithRules()
    }

    private fun validateDataWithRules() {
        val firstNameResult = Validator.validateFirstName(signUpState.value.firstName)
        val lastNameResult = Validator.validateLastName(signUpState.value.lastName)
        val emailResult = Validator.validateEmail(signUpState.value.email)
        val passwordResult = Validator.validatePassword(signUpState.value.password)

        Log.d(TAG, "validateDataWithRules: firstName: $firstNameResult")
        Log.d(TAG, "validateDataWithRules: lastName: $lastNameResult")
        Log.d(TAG, "validateDataWithRules: email: $emailResult")
        Log.d(TAG, "validateDataWithRules: password: $passwordResult")


        _signUpState.value = _signUpState.value.copy(
            firstNameError = firstNameResult.status,
            lastNameError = lastNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
    }
}
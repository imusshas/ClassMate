package com.nasiat_muhib.classmate.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nasiat_muhib.classmate.domain.event.UIEvent
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

    fun event(event: UIEvent) {
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
        }
    }


    private fun printState() {
        Log.d(TAG, "printState: ${signUpState.value}")
    }
}
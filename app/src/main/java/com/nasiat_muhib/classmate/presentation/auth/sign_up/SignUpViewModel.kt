package com.nasiat_muhib.classmate.presentation.auth.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.SignUpUIEvent
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.domain.rules.AuthValidator
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.domain.state.SignUpUIState
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo: AuthenticationRepository
) : ViewModel() {

    private val _signUpUIState = MutableStateFlow(SignUpUIState())
    val signUpUIState = _signUpUIState.asStateFlow()

    private val _signUpDataState = MutableStateFlow<DataState<Boolean>>(DataState.Success(false)) // TODO: Try DataSource.Loading()
    val signUpDataState = _signUpDataState.asStateFlow()

    private val _allValidationPassed = MutableStateFlow(false)
    private val allValidationPassed = _allValidationPassed.asStateFlow()


    fun onEvent(event: SignUpUIEvent) {
        when (event) {
            is SignUpUIEvent.FirstNameChanged -> {
                _signUpUIState.value = _signUpUIState.value.copy(firstName = event.firstName)
            }

            is SignUpUIEvent.LastNameChanged -> {
                _signUpUIState.value = _signUpUIState.value.copy(lastName = event.lastName)
            }

            is SignUpUIEvent.RoleChanged -> {
                _signUpUIState.value = _signUpUIState.value.copy(role = event.role)
            }

            is SignUpUIEvent.EmailChanged -> {
                _signUpUIState.value = _signUpUIState.value.copy(email = event.email)
            }

            is SignUpUIEvent.PasswordChanged -> {
                _signUpUIState.value = _signUpUIState.value.copy(password = event.password)
            }

            SignUpUIEvent.SignUpButtonClicked ->  {
                signUp()
            }

            is SignUpUIEvent.DepartmentChanged -> {
                _signUpUIState.value = _signUpUIState.value.copy(department = event.department)
            }
        }
    }

    private fun signUp() = viewModelScope.launch (Dispatchers.IO) {
        validateSignUpDataWithRules()
        if(allValidationPassed.value) {
            val user = User (
                firstName = signUpUIState.value.firstName,
                lastName = signUpUIState.value.lastName,
                role = signUpUIState.value.role,
                department = signUpUIState.value.department,
                email = signUpUIState.value.email,
            )

            authRepo.signUp(signUpUIState.value.email, signUpUIState.value.password, user).collectLatest {
                Log.d(TAG, "signUp: $user")
                _signUpDataState.value = it
            }
        }
    }

    private fun validateSignUpDataWithRules() {
        val firstNameResult = AuthValidator.validateFirstName(signUpUIState.value.firstName)
        val lastNameResult = AuthValidator.validateLastName(signUpUIState.value.lastName)
        val departmentResult = AuthValidator.validateDepartment(signUpUIState.value.department)
        val emailResult = AuthValidator.validateEmail(signUpUIState.value.email)
        val passwordResult = AuthValidator.validatePassword(signUpUIState.value.password)


        _signUpUIState.value = _signUpUIState.value.copy(
            firstNameError = firstNameResult.message,
            lastNameError = lastNameResult.message,
            departmentError = departmentResult.message,
            emailError = emailResult.message,
            passwordError = passwordResult.message
        )

        Log.d(TAG, "validateSignUpDataWithRules: $firstNameResult, $lastNameResult, $emailResult, $passwordResult")
        _allValidationPassed.value =
            firstNameResult.message == null && lastNameResult.message == null && emailResult.message == null && passwordResult.message == null && departmentResult.message == null
    }

    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        authRepo.signOut().collectLatest {
            _signUpDataState.value = it
        }
    }

    companion object {
        const val TAG = "SignUpViewModel"
    }
}
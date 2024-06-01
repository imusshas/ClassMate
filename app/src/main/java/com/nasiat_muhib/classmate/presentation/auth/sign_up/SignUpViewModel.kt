package com.nasiat_muhib.classmate.presentation.auth.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.data.model.api_response.RequestOTPResponse
import com.nasiat_muhib.classmate.data.model.api_response.VerifyOTPResponse
import com.nasiat_muhib.classmate.domain.event.SignUpUIEvent
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.domain.repository.BdAppsApiRepository
import com.nasiat_muhib.classmate.domain.rules.AuthValidator
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.domain.state.SignUpUIState
import com.nasiat_muhib.classmate.strings.INVALID_OPERATOR
import com.nasiat_muhib.classmate.strings.REGISTERED
import com.nasiat_muhib.classmate.strings.SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo: AuthenticationRepository,
    private val bdAppsApiRepo: BdAppsApiRepository,
) : ViewModel() {

    private val _signUpUIState = MutableStateFlow(SignUpUIState())
    val signUpUIState = _signUpUIState.asStateFlow()

    private val _signUpDataState =
        MutableStateFlow<DataState<FirebaseUser?>>(DataState.Success(null))
    val signUpDataState = _signUpDataState.asStateFlow()

    private val _allValidationPassed = MutableStateFlow(false)
    private val allValidationPassed = _allValidationPassed.asStateFlow()

    private val _otpScreenState = MutableStateFlow(false)
    val otpScreenState = _otpScreenState.asStateFlow()

    private val _navigateToHomeScreenState = MutableStateFlow(false)
    val navigateToHomeScreenState = _navigateToHomeScreenState.asStateFlow()

    private val _requestOTP = MutableStateFlow<RequestOTPResponse?>(null)
    private val requestOTP = _requestOTP.asStateFlow()

    private val _verifyOTP = MutableStateFlow<VerifyOTPResponse?>(null)
    private val verifyOTP = _verifyOTP.asStateFlow()


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

            SignUpUIEvent.SignUpButtonClicked -> {
                validateSignUpDataWithRules()
                if (allValidationPassed.value) {
                    requestOTP()
                }
            }

            is SignUpUIEvent.DepartmentChanged -> {
                _signUpUIState.value = _signUpUIState.value.copy(department = event.department)
            }

            is SignUpUIEvent.PhoneNoChanged -> {
                _signUpUIState.value = _signUpUIState.value.copy(phoneNo = event.phoneNo)
            }

            SignUpUIEvent.VerifyOTPButtonClicked -> {
                verifyOTP()

            }

            is SignUpUIEvent.OTPChanged -> {
                _signUpUIState.value = _signUpUIState.value.copy(otp = event.otp)
            }
        }
    }

    private fun requestOTP() = viewModelScope.launch {

        val phoneNo = signUpUIState.value.phoneNo

        val subscriberId = if (phoneNo[0] == '+' && phoneNo[1] == '8' && phoneNo[2] == '8') {
            phoneNo.substring(3)
        } else if (phoneNo[0] == '8' && phoneNo[1] == '8') {
            phoneNo.substring(2)
        } else {
            phoneNo
        }

        bdAppsApiRepo.requestOTP(subscriberId = subscriberId).collectLatest { response ->
            Log.d(TAG, "requestOTP: ${signUpUIState.value.phoneNo}, response: ${response.body()}")
            Log.d(TAG, "requestOTP: subscriberId: $subscriberId")

            if (response.isSuccessful) {
                _requestOTP.value = response.body()

                if (requestOTP.value?.statusDetail == SUCCESS) {
                    _otpScreenState.update { true }
                } else if (requestOTP.value?.statusDetail == REGISTERED) {
                    _signUpUIState.value =
                        signUpUIState.value.copy(phoneNoError = "User is already registered")
                } else if (requestOTP.value?.statusDetail == INVALID_OPERATOR) {
                    _signUpUIState.value =
                        signUpUIState.value.copy(phoneNoError = "Provider must be a Robi operator")
                }
            } else {
                Log.d(TAG, "requestOTP: ${response.errorBody()}")
            }
        }
    }

    private fun verifyOTP() = viewModelScope.launch {
        requestOTP.value?.let { requestOTPResponse ->
             bdAppsApiRepo.verifyOTP(
                referenceNo = requestOTPResponse.referenceNo,
                otp = signUpUIState.value.otp
            ).collectLatest { response ->
                 if (response.isSuccessful) {
                     _verifyOTP.value = response.body()
                     if (verifyOTP.value?.statusDetail == SUCCESS) {
                         signUp()
                     } else {
                         _signUpUIState.value = signUpUIState.value.copy(otpError = "Invalid OTP")
                     }
                 } else {
                     Log.d(TAG, "verifyOTP: ${response.errorBody()}")
                 }
            }
        }
    }

    private fun signUp() = viewModelScope.launch(Dispatchers.IO) {
        validateSignUpDataWithRules()
        if (allValidationPassed.value) {
            val user = User(
                firstName = signUpUIState.value.firstName,
                lastName = signUpUIState.value.lastName,
                role = signUpUIState.value.role,
                department = signUpUIState.value.department,
                phoneNo = signUpUIState.value.phoneNo,
                email = signUpUIState.value.email,
            )
            authRepo.signUp(signUpUIState.value.email, signUpUIState.value.password, user)
                .collectLatest {
                    _signUpDataState.value = it
                    _signUpUIState.value = signUpUIState.value.copy(emailError = it.error)
                    if (it.error == null && it.data != null) {
                        _navigateToHomeScreenState.update { true }
                        _otpScreenState.value = false
                    }
                }
        }
    }

    private fun validateSignUpDataWithRules() {
        val firstNameResult = AuthValidator.validateFirstName(signUpUIState.value.firstName)
        val lastNameResult = AuthValidator.validateLastName(signUpUIState.value.lastName)
        val departmentResult = AuthValidator.validateDepartment(signUpUIState.value.department)
        val phoneNoResult = AuthValidator.validatePhoneNo(signUpUIState.value.phoneNo)
        val emailResult = AuthValidator.validateEmail(signUpUIState.value.email)
        val passwordResult = AuthValidator.validatePassword(signUpUIState.value.password)


        _signUpUIState.value = _signUpUIState.value.copy(
            firstNameError = firstNameResult.message,
            lastNameError = lastNameResult.message,
            departmentError = departmentResult.message,
            phoneNoError = phoneNoResult.message,
            emailError = emailResult.message,
            passwordError = passwordResult.message
        )
        _allValidationPassed.value =
            firstNameResult.message == null &&
                    lastNameResult.message == null &&
                    departmentResult.message == null &&
                    phoneNoResult.message == null &&
                    emailResult.message == null &&
                    passwordResult.message == null
    }

    companion object {
        const val TAG = "SignUpViewModel"
    }
}
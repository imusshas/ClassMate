package com.nasiat_muhib.classmate.presentation.auth.sign_up


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.AuthRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo: AuthRepository,
): ViewModel() {

    private var _signUpState = MutableStateFlow<ResponseState<Boolean>>(ResponseState.Success(true))
    val signUpState = _signUpState.asStateFlow()

    fun signUp(email: String, password: String, user: User) = viewModelScope.launch {
        authRepo.signUp(email, password, user).collect {
            _signUpState.value = it
        }
    }
}
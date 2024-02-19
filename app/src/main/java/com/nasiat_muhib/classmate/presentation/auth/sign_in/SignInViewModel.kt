package com.nasiat_muhib.classmate.presentation.auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo: AuthRepository
):ViewModel() {

    private var _signInState = MutableStateFlow<ResponseState<Boolean>>(ResponseState.Success(true))
    val signInState = _signInState.asStateFlow()

    fun signIn(email: String, password: String) = viewModelScope.launch {
        authRepo.signIn(email, password).collect {
            _signInState.value = it
        }
    }


    fun signOut() = viewModelScope.launch {
        authRepo.signOut().collect {
            _signInState.value = it
        }
    }
}
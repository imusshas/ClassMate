package com.nasiat_muhib.classmate.presentation.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val authRepo: AuthRepository
): ViewModel() {

    private var _menuState = MutableStateFlow<ResponseState<Boolean>>(ResponseState.Success(true))
    val menuState = _menuState.asStateFlow()

    fun signOut() = viewModelScope.launch {
        authRepo.signOut().collectLatest {
            _menuState.value = it
        }
    }
}
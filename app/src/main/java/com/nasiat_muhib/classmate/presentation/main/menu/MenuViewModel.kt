package com.nasiat_muhib.classmate.presentation.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.domain.event.MenuUIEvent
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val authRepo: AuthenticationRepository,
) : ViewModel() {

    fun onEvent(event: MenuUIEvent) {
        when (event) {
            MenuUIEvent.SignOutButtonClicked -> {
                signOut()
            }
        }
    }
    private fun signOut() = viewModelScope.launch {
        authRepo.signOut().collectLatest {
        }
    }

    companion object {
        const val TAG ="MenuViewModel"
    }
}
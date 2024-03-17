package com.nasiat_muhib.classmate.presentation.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.domain.event.MenuUIEvent
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val authRepo: AuthenticationRepository
) : ViewModel() {

    fun onMenuEvent(event: MenuUIEvent) {

        when (event) {
            MenuUIEvent.ProfileButtonClicked -> {
                ClassMateAppRouter.navigateTo(Screen.ProfileScreen)
            }
            MenuUIEvent.RoutineButtonClicked -> TODO()
            MenuUIEvent.SignOutButtonClicked -> {
                signOut()
            }
        }
    }

    private fun signOut() = viewModelScope.launch {
        authRepo.signOut().collectLatest {

        }
    }
}
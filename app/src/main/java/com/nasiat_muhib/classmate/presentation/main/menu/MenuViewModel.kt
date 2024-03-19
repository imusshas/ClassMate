package com.nasiat_muhib.classmate.presentation.main.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nasiat_muhib.classmate.domain.event.MenuUIEvent
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val authRepo: AuthenticationRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    fun onMenuEvent(event: MenuUIEvent) {

        when (event) {
            MenuUIEvent.ProfileButtonClicked -> {
                ClassMateAppRouter.navigateTo(Screen.ProfileScreen)
            }
            MenuUIEvent.RoutineButtonClicked -> {
                ClassMateAppRouter.navigateTo(Screen.RoutineScreen)
            }
            MenuUIEvent.SignOutButtonClicked -> {
                signOut()
            }
        }
    }

    private fun signOut() = viewModelScope.launch {
        authRepo.signOut().collectLatest {
            Log.d(TAG, "signOut: ${userRepo.currentUser}")
            Log.d(TAG, "signOut: ${Firebase.auth.currentUser}")
        }
    }

    companion object {
        const val TAG ="MenuViewModel"
    }
}
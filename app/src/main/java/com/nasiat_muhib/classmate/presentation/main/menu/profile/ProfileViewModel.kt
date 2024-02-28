package com.nasiat_muhib.classmate.presentation.main.menu.profile


import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.nasiat_muhib.classmate.core.Constants.TAG
import com.nasiat_muhib.classmate.core.Constants.USERS_COLLECTION
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    auth: FirebaseAuth,
    private val userRepo: UserRepository
): ViewModel() {

    private val _userState = MutableStateFlow<ResponseState<User>>(ResponseState.Success(User()))
    val userState = _userState.asStateFlow()

    init {
        viewModelScope.launch {
            if(auth.currentUser != null) {
//                Log.d(TAG, "ProfileViewModel : ${auth.currentUser?.email!!}")
                getUser(auth.currentUser?.email!!)
            }
        }
    }

    fun updateUser(email: String, user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.updateUser(email, user).collect {
            _userState.value = it
        }
    }

    private fun getUser(email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.getUser(email).collect {
            _userState.value = it
        }
    }

}
package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSemesterViewModel @Inject constructor(
   private val userRepo: UserRepository,
): ViewModel() {

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "init: Inside Init")
            getUser(userRepo.currentUser.email!!)
        }
    }

    fun getUser(email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.getUser(email).collectLatest {
            _userState.value = it
            Log.d(TAG, "getUser: $it")
        }
    }

    companion object {
        private const val TAG = "CreateSemesterViewModel"
    }
}
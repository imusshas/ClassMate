package com.nasiat_muhib.classmate.presentation.main.menu.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.EditProfileUIEvent
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.rules.EditProfileValidator
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.domain.state.ProfileUIState
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
) : ViewModel() {

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val userState = _userState.asStateFlow()

    private val _editUserState = MutableStateFlow(ProfileUIState())
    val editUserState = _editUserState.asStateFlow()

    private val _editProfileButtonState = MutableStateFlow(false)
    val editProfileButtonState = _editProfileButtonState.asStateFlow()

    private val _allEditProfileValidationPassed = MutableStateFlow(false)
    private val allEditProfileValidationPassed = _allEditProfileValidationPassed.asStateFlow()

    fun getUser() = viewModelScope.launch {
        userRepo.getCurrentUser("ProfileViewModel").collectLatest {
            _userState.value = it
        }
    }

    fun onEditProfileEvent(event: EditProfileUIEvent) {

        when (event) {
            is EditProfileUIEvent.BloodGroupChanged -> {
                _editUserState.value = editUserState.value.copy(bloodGroup = event.bloodGroup)
            }

            is EditProfileUIEvent.DepartmentChanged -> {
                _editUserState.value = editUserState.value.copy(department = event.department)
            }

            is EditProfileUIEvent.FirstNameChanged -> {
                _editUserState.value = editUserState.value.copy(firstName = event.firstName)
            }

            is EditProfileUIEvent.LastNameChanged -> {
                _editUserState.value = editUserState.value.copy(lastName = event.lastName)
            }

            is EditProfileUIEvent.PhoneNoChanged -> {
                _editUserState.value = editUserState.value.copy(phoneNo = event.phoneNo)
            }

            is EditProfileUIEvent.RoleChanged -> {
                _editUserState.value = editUserState.value.copy(role = event.role)
            }

            EditProfileUIEvent.DoneButtonClicked -> {
                onEdit()
            }

            EditProfileUIEvent.EditButtonClicked -> {
                _editProfileButtonState.value = true
            }

            EditProfileUIEvent.GoBackIconClicked -> {
                ClassMateAppRouter.navigateTo(Screen.MenuScreen)
            }

            is EditProfileUIEvent.SessionChanged -> {
                _editUserState.value = editUserState.value.copy(session = event.session)
            }
        }
    }

    private fun onEdit() = viewModelScope.launch {
        validateEditProfileUIDataWithRules()
        if (allEditProfileValidationPassed.value) {
            _editProfileButtonState.value = false
            userState.value.data?.let {
                val user = User(
                    firstName = editUserState.value.firstName.ifBlank { it.firstName },
                    lastName = editUserState.value.lastName.ifBlank { it.lastName },
                    role = editUserState.value.role.ifBlank { it.role },
                    department = it.department,
                    session = editUserState.value.session.ifBlank { it.session },
                    bloodGroup = editUserState.value.bloodGroup.ifBlank { it.bloodGroup },
                    phoneNo = editUserState.value.phoneNo.ifBlank { it.phoneNo },
                    email = it.email,
                    courses = it.courses,
                    requestedCourses = it.requestedCourses
                )
                val result = hasBeenEdited(user = it)
                if (result) {
                    userRepo.updateUser(user).collectLatest {

                    }
                }
                Log.d(TAG, "onEdit: :has been edited: $result: user: $user")
            }
        }
    }

    private fun validateEditProfileUIDataWithRules() {
        val sessionResult = EditProfileValidator.validateSession(editUserState.value.session)
        val bloodGroupResult =
            EditProfileValidator.validateBloodGroup(editUserState.value.bloodGroup)
        val phoneNoResult = EditProfileValidator.validatePhoneNo(editUserState.value.phoneNo)

        _editUserState.value = editUserState.value.copy(
            sessionError = sessionResult.message,
            bloodGroupError = bloodGroupResult.message,
            phoneNoError = phoneNoResult.message
        )


        _allEditProfileValidationPassed.value =
            sessionResult.message == null && bloodGroupResult.message == null && phoneNoResult.message == null
    }

    private fun hasBeenEdited(user: User): Boolean {
        return (user.firstName != editUserState.value.firstName && editUserState.value.firstName.isNotBlank()) ||
                (user.lastName != editUserState.value.lastName && editUserState.value.lastName.isNotBlank()) ||
                (user.role != editUserState.value.role && editUserState.value.role.isNotBlank()) ||
                (user.session != editUserState.value.session && editUserState.value.session.isNotBlank()) ||
                (user.bloodGroup != editUserState.value.bloodGroup && editUserState.value.bloodGroup.isNotBlank()) ||
                (user.phoneNo != editUserState.value.phoneNo && editUserState.value.phoneNo.isNotBlank())
    }

    companion object {
        const val TAG = "ProfileViewModel"
    }
}
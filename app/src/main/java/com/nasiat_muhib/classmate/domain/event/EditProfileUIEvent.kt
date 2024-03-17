package com.nasiat_muhib.classmate.domain.event

sealed class EditProfileUIEvent {
    data class FirstNameChanged(val firstName: String): EditProfileUIEvent()
    data class LastNameChanged(val lastName: String): EditProfileUIEvent()
    data class RoleChanged(val role: String): EditProfileUIEvent()
    data class DepartmentChanged(val department: String): EditProfileUIEvent()
    data class SessionChanged(val session: String): EditProfileUIEvent()
    data class BloodGroupChanged(val bloodGroup: String): EditProfileUIEvent()
    data class PhoneNoChanged(val phoneNo: String): EditProfileUIEvent()
    data object EditButtonClicked: EditProfileUIEvent()
    data object DoneButtonClicked: EditProfileUIEvent()
    data object GoBackIconClicked: EditProfileUIEvent()
}
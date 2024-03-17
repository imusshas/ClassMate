package com.nasiat_muhib.classmate.domain.state

data class ProfileUIState(
    val firstName: String = "",
    val lastName: String = "",
    val role: String = "",
    val department: String = "",
    val session: String = "",
    val bloodGroup: String = "",
    val phoneNo: String = "",


    val sessionError: String? = null,
    val bloodGroupError: String? = null,
    val phoneNoError: String? = null,
)

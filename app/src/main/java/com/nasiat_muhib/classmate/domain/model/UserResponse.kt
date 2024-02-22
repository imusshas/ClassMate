package com.nasiat_muhib.classmate.domain.model

import com.google.firebase.firestore.auth.User

data class UserResponse(
    var userData: User? = null,
    var error: String? = null
)

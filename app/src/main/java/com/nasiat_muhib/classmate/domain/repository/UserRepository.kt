package com.nasiat_muhib.classmate.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val currentUser: FirebaseUser

    fun getUser(email: String): Flow<DataState<User>>

    fun updateUser(user: User): Flow<DataState<User>>
}
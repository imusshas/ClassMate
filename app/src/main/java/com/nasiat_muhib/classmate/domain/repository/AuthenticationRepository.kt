package com.nasiat_muhib.classmate.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun signIn(email: String, password: String): Flow<DataState<FirebaseUser?>>
    fun signUp(email: String, password: String, user: User): Flow<DataState<FirebaseUser?>>
    fun signOut(): Flow<DataState<Boolean>>
    fun resetPassword(email: String): Flow<DataState<Boolean>>
}
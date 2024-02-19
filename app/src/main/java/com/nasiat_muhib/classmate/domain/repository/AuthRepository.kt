package com.nasiat_muhib.classmate.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun signUp(email: String, password: String, user: User): Flow<ResponseState<Boolean>>

    suspend fun signIn(email: String, password: String): Flow<ResponseState<Boolean>>

    suspend fun signOut(): Flow<ResponseState<Boolean>>
}
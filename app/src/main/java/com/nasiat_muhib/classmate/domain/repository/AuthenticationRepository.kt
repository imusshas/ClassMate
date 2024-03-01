package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun signIn(email: String, password: String): Flow<DataState<Boolean>>
    fun signUp(email: String, password: String, user: User): Flow<DataState<Boolean>>
    fun signOut(): Flow<DataState<Boolean>>
}
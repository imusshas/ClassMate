package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun createUser(user: User): Flow<ResponseState<User>>

    suspend fun updateUser(email: String, user: User): Flow<ResponseState<User>>

    suspend fun getUser(email: String): Flow<ResponseState<User>>
}
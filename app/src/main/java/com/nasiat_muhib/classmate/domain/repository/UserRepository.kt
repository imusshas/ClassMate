package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.model.ResponseState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun updateUser(email: String, user: User): Flow<ResponseState<User>>

    fun getUser(email: String): Flow<ResponseState<User>>
}
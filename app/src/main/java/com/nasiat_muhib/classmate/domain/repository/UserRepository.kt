package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser (email: String): Flow<DataState<User>>

    fun updateUser(user: User): Flow<DataState<User>>
}
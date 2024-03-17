package com.nasiat_muhib.classmate.domain.repository

import com.nasiat_muhib.classmate.data.model.Post
import com.nasiat_muhib.classmate.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun createPost(post: Post): Flow<DataState<Post>>
    fun getPosts(): Flow<List<Post>>
    fun deletePost(post: Post): Flow<DataState<Post>>

}
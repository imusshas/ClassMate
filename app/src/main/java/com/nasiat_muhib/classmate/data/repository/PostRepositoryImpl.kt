package com.nasiat_muhib.classmate.data.repository

import android.icu.text.SimpleDateFormat
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.core.GetModelFromDocument.getPostFromFirestoreDocument
import com.nasiat_muhib.classmate.data.model.Post
import com.nasiat_muhib.classmate.domain.repository.PostRepository
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.strings.POSTS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore
): PostRepository {
    private val postsCollection = firestoreRef.collection(POSTS_COLLECTION)
    override fun createPost(post: Post): Flow<DataState<Post>> = flow {
        emit(DataState.Loading)
        val postId = "${post.creator}:${post.timestamp}"
        val date = Date(post.timestamp)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Define your desired format
        val dateTimeString = formatter.format(date)
        Log.d(TAG, "createPost: $dateTimeString")
        postsCollection.document(postId).set(post.toMap()).await()
        emit(DataState.Success(post))
    }.catch {
        Log.d(TAG, "createPost: ${it.localizedMessage}")
    }

    override fun getPosts(): Flow<List<Post>> = callbackFlow {
        val posts = mutableListOf<Post>()
        val snapshotListener = postsCollection.addSnapshotListener { value, error ->
            value?.documents?.forEach {
                val post = getPostFromFirestoreDocument(it)
                posts.add(post)
            }

            trySend(posts).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
        Log.d(TAG, "getPosts: ${it.localizedMessage}")
    }

    override fun deletePost(post: Post): Flow<DataState<Post>> = flow {
        emit(DataState.Loading)
        val postId = "${post.creator}:${post.timestamp}"
        postsCollection.document(postId).delete().await()
        emit(DataState.Success(post))
    }.catch {
        Log.d(TAG, "deletePost: ${it.localizedMessage}")
    }

    companion object {
        const val TAG = "PostRepositoryImpl"
    }

}
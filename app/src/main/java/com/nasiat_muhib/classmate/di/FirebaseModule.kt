package com.nasiat_muhib.classmate.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.nasiat_muhib.classmate.core.Constants.COURSES_COLLECTION
import com.nasiat_muhib.classmate.core.Constants.USERS_COLLECTION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun providesFirebaseAuthentication(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun providesUserCollection(): CollectionReference = Firebase.firestore.collection(USERS_COLLECTION)


}
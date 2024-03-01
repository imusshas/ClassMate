package com.nasiat_muhib.classmate.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.data.repository.AuthenticationRepositoryImpl
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesAuthenticationRepository(
        auth: FirebaseAuth, firestoreRef: FirebaseFirestore
    ): AuthenticationRepository = AuthenticationRepositoryImpl(
        auth, firestoreRef
    )
}
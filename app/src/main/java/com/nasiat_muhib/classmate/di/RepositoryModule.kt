package com.nasiat_muhib.classmate.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nasiat_muhib.classmate.data.repository.AuthenticationRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.CourseRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.UserRepositoryImpl
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.data.repository.SearchRepositoryImpl
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.repository.SearchRepository
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

    @Provides
    @Singleton
    fun providesUserRepository(
        auth: FirebaseAuth, firestoreRef: FirebaseFirestore
    ): UserRepository = UserRepositoryImpl(
        auth, firestoreRef
    )

    @Provides
    @Singleton
    fun providesSearchRepository (
        firestoreRef: FirebaseFirestore
    ): SearchRepository = SearchRepositoryImpl(firestoreRef)

    @Provides
    @Singleton

    fun providesCourseRepository (
        firestoreRef: FirebaseFirestore
    ): CourseRepository = CourseRepositoryImpl (firestoreRef)
}
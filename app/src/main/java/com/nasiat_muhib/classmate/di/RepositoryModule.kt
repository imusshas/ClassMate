package com.nasiat_muhib.classmate.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.nasiat_muhib.classmate.core.Constants.USERS_COLLECTION
import com.nasiat_muhib.classmate.data.repository.AuthRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.ClassDetailsRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.CourseRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.UserRepositoryImpl
import com.nasiat_muhib.classmate.domain.repository.AuthRepository
import com.nasiat_muhib.classmate.domain.repository.ClassDetailsRepository
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesAuthenticationRepository(): AuthRepository =
        AuthRepositoryImpl(
            auth = Firebase.auth,
            usersCollection = Firebase.firestore.collection(USERS_COLLECTION)
        )

    @Singleton
    @Provides
    fun providesUserRepository(): UserRepository =
        UserRepositoryImpl(
            firestoreRef = Firebase.firestore
        )

    @Singleton
    @Provides
    fun providesCourseRepository(): CourseRepository =
        CourseRepositoryImpl(
            firestoreRef = Firebase.firestore,
            auth = Firebase.auth
        )

    @Singleton
    @Provides
    fun providesClassDetailsRepository(): ClassDetailsRepository =
        ClassDetailsRepositoryImpl(
            auth = Firebase.auth,
            firestoreRef = Firebase.firestore
        )
}
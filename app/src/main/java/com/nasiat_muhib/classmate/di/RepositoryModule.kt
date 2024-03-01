package com.nasiat_muhib.classmate.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

//    @Singleton
//    @Provides
//    fun providesAuthenticationRepository(): AuthRepository =
//        AuthRepositoryImpl(
//            auth = Firebase.auth,
//            usersCollection = Firebase.firestore.collection(USERS_COLLECTION)
//        )
//
//    @Singleton
//    @Provides
//    fun providesUserRepository(): UserRepository =
//        UserRepositoryImpl(
//            firestoreRef = Firebase.firestore
//        )
//
//    @Singleton
//    @Provides
//    fun providesCourseRepository(): CourseRepository =
//        CourseRepositoryImpl(
//            firestoreRef = Firebase.firestore,
//        )
//
//    @Singleton
//    @Provides
//    fun providesClassDetailsRepository(): ClassDetailsRepository =
//        ClassDetailsRepositoryImpl(
//            firestoreRef = Firebase.firestore
//        )
}
package com.nasiat_muhib.classmate.di

import android.app.Notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.nasiat_muhib.classmate.data.repository.AuthenticationRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.ClassDetailsRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.CourseRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.EventRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.NotificationRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.PostRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.ResourceLinkRepositoryImpl
import com.nasiat_muhib.classmate.data.repository.UserRepositoryImpl
import com.nasiat_muhib.classmate.domain.repository.AuthenticationRepository
import com.nasiat_muhib.classmate.data.repository.SearchRepositoryImpl
import com.nasiat_muhib.classmate.domain.repository.ClassDetailsRepository
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.EventRepository
import com.nasiat_muhib.classmate.domain.repository.NotificationRepository
import com.nasiat_muhib.classmate.domain.repository.PostRepository
import com.nasiat_muhib.classmate.domain.repository.ResourceLinkRepository
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
        auth: FirebaseAuth, firestoreRef: FirebaseFirestore,
    ): AuthenticationRepository = AuthenticationRepositoryImpl(
        auth, firestoreRef
    )

    @Provides
    @Singleton
    fun providesUserRepository(
        firestoreRef: FirebaseFirestore,
    ): UserRepository = UserRepositoryImpl(
        firestoreRef
    )

    @Provides
    @Singleton
    fun providesSearchRepository(
        firestoreRef: FirebaseFirestore,
    ): SearchRepository = SearchRepositoryImpl(firestoreRef)

    @Provides
    @Singleton
    fun providesCourseRepository(
        firestoreRef: FirebaseFirestore,
    ): CourseRepository = CourseRepositoryImpl(firestoreRef)

    @Provides
    @Singleton
    fun providesEventRepository(
        firestoreRef: FirebaseFirestore,
    ): EventRepository = EventRepositoryImpl(firestoreRef)


    @Provides
    @Singleton
    fun providesClassDetailsRepository(
        firestoreRef: FirebaseFirestore,
    ): ClassDetailsRepository = ClassDetailsRepositoryImpl(firestoreRef)

    @Provides
    @Singleton
    fun providesPostRepository(
        firestoreRef: FirebaseFirestore,
    ): PostRepository = PostRepositoryImpl(firestoreRef)

    @Provides
    @Singleton
    fun providesResourceLinkRepository(
        firestoreRef: FirebaseFirestore,
    ): ResourceLinkRepository = ResourceLinkRepositoryImpl(firestoreRef)

    @Provides
    @Singleton
    fun providesNotificationRepository(
        auth: FirebaseAuth,
        firestoreRef: FirebaseFirestore,
    ): NotificationRepository = NotificationRepositoryImpl(auth, firestoreRef)
}
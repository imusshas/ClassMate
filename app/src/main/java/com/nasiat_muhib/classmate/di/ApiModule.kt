package com.nasiat_muhib.classmate.di

import com.nasiat_muhib.classmate.api.BdAppsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    private const val BASE_URL = "http://20.197.50.116:1389/"
    @Singleton
    @Provides
    fun providesRetrofitApi(): BdAppsApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(BdAppsApi::class.java)

}
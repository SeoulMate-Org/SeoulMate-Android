package com.seoulmate.data.di

import com.seoulmate.data.api.NaverMapApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun naverMapApiService(@NetworkModule.NaverMapNetwork naverMapRetrofit: Retrofit): NaverMapApiService =
        naverMapRetrofit.create(NaverMapApiService::class.java)
}
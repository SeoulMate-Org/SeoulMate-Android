package com.seoulmate.data.di

import com.seoulmate.data.api.NaverMapApiService
import com.seoulmate.data.repository.GeocodeRepository
import com.seoulmate.data.repository.impl.GeocodeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providerGeocodeRepository(
        service: NaverMapApiService,
    ): GeocodeRepository = GeocodeRepositoryImpl(
        service
    )
}
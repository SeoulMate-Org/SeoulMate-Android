package com.seoulmate.data.di

import com.seoulmate.data.api.ApiService
import com.seoulmate.data.api.ApiServiceExceptToke
import com.seoulmate.data.api.NaverMapApiService
import com.seoulmate.data.repository.ChallengeRepository
import com.seoulmate.data.repository.GeocodeRepository
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.data.repository.LoginRepository
import com.seoulmate.data.repository.UserRepository
import com.seoulmate.data.repository.impl.ChallengeRepositoryImpl
import com.seoulmate.data.repository.impl.GeocodeRepositoryImpl
import com.seoulmate.data.repository.impl.PreferDataStoreRepositoryImpl
import com.seoulmate.data.repository.impl.LoginRepositoryImpl
import com.seoulmate.data.repository.impl.UserRepositoryImpl
import com.seoulmate.datastore.repository.DataStoreRepository
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

    @Provides
    @Singleton
    fun providerLoginRepository(
        service: ApiServiceExceptToke,
    ): LoginRepository = LoginRepositoryImpl(
        service
    )

    @Provides
    @Singleton
    fun providerLanguageRepository(
        dataStore: DataStoreRepository
    ): PreferDataStoreRepository = PreferDataStoreRepositoryImpl(
        dataStore,
    )

    @Provides
    @Singleton
    fun providerChallengeRepository(
        service: ApiService,
    ): ChallengeRepository = ChallengeRepositoryImpl(
        service
    )

    @Provides
    @Singleton
    fun providerUserRepository(
        service: ApiService,
    ): UserRepository = UserRepositoryImpl(
        service
    )

}
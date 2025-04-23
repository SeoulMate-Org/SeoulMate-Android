package com.seoulmate.database.di

import com.seoulmate.database.PopPopSeoulDataBase
import com.seoulmate.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModel {

    @Provides
    fun providesUserDao(
        dataBase: PopPopSeoulDataBase,
    ): UserDao = dataBase.userDao()

}
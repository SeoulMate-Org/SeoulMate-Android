package com.seoulmate.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seoulmate.database.PopPopSeoulDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModel {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): PopPopSeoulDataBase = Room.databaseBuilder(
        context,
        PopPopSeoulDataBase::class.java,
        "pps-database"
    ).build()

}
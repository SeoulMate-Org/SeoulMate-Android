package com.seoulmate.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seoulmate.database.dao.UserDao
import com.seoulmate.database.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
    ],
    version = 1,
    autoMigrations = [],
    exportSchema = true,
)
internal abstract class PopPopSeoulDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
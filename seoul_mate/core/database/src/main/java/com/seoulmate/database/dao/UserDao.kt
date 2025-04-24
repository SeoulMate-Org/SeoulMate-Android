package com.seoulmate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.seoulmate.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query(
        "SELECT * FROM userInfo"
    )
    fun getUser(): Flow<UserEntity?>

    @Upsert
    suspend fun upsertUser(user: UserEntity)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)

}
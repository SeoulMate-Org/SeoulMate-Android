package com.seoulmate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.seoulmate.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query(
        "SELECT * FROM userInfo"
    )
    fun getUser(): Flow<UserEntity?>

    @Update
    suspend fun updateUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM userInfo")
    suspend fun deleteUser()

//    @Insert(onConflict = OnConflictStrategy.IGNORE)

}
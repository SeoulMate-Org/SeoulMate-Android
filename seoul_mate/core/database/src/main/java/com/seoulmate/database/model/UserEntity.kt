package com.seoulmate.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seoulmate.data.model.user.UserData

@Entity (
    tableName = "userInfo"
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(defaultValue = "")
    val nickName: String,
    @ColumnInfo(defaultValue = "")
    val loginType: String,
    @ColumnInfo(defaultValue = "")
    val accessToken: String,
    @ColumnInfo(defaultValue = "")
    val refreshToken: String,
)

fun UserEntity.asExternalModel() = UserData(
    nickName = nickName,
    accessToken = accessToken,
    refreshToken = refreshToken,
    loginType = loginType,
)
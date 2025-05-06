package com.seoulmate.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seoulmate.data.model.user.UserData

@Entity (
    tableName = "userInfo"
)
data class UserEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(defaultValue = "", name = "nickName")
    val nickName: String,
    @ColumnInfo(defaultValue = "", name = "loginType")
    val loginType: String,
    @ColumnInfo(defaultValue = "", name = "accessToken")
    val accessToken: String,
    @ColumnInfo(defaultValue = "", name = "refreshToken")
    val refreshToken: String,
)

fun UserEntity.asExternalModel() = UserData(
    id = id,
    nickName = nickName,
    accessToken = accessToken,
    refreshToken = refreshToken,
    loginType = loginType,
)
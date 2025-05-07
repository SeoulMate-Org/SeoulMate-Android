package com.seoulmate.data.repository

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.user.DeleteUserDto
import com.seoulmate.data.dto.user.UserInfoDto
import com.seoulmate.data.dto.user.UserNicknameDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    // put user nickname
    suspend fun reqUserNickname(nickname: String): Flow<CommonDto<UserNicknameDto>>

    // fetch user info
    suspend fun reqUserInfo(): Flow<CommonDto<UserInfoDto?>>

    // delete user
    suspend fun deleteUser(
        userId: Int
    ): Flow<CommonDto<DeleteUserDto?>>
}
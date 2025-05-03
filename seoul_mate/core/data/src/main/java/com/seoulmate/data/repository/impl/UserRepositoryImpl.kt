package com.seoulmate.data.repository.impl

import com.seoulmate.data.api.ApiService
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.user.UserInfoDto
import com.seoulmate.data.dto.user.UserNicknameDto
import com.seoulmate.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
): UserRepository {

    override suspend fun reqUserNickname(
        nickname: String
    ): Flow<CommonDto<UserNicknameDto>> = flow {
        val response = apiService.reqUserNickname(
            nickname = nickname,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }

    override suspend fun reqUserInfo(): Flow<CommonDto<UserInfoDto?>> = flow {
        val response = apiService.reqUserInfo()
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }

}
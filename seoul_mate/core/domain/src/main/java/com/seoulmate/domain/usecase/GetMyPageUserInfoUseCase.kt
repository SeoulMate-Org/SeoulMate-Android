package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.user.UserInfoData
import com.seoulmate.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyPageUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Flow<CommonDto<UserInfoData?>> = userRepository
        .reqUserInfo().map {
            if (it.response == null) {
                CommonDto(
                    code = it.code,
                    message = it.message,
                    response = null,
                )
            } else {
                var userInfo: UserInfoData? = null
                it.response?.let { response ->
                    userInfo = UserInfoData(
                        id = response.id,
                        email = response.email ?: "",
                        nickname = response.nickname ?: "",
                        loginType = response.loginType ?: "",
                        likeCount = response.likeCount ?: 0,
                        badgeCount = response.badgeCount ?: 0,
                        commentCount = response.commentCount ?: 0,
                    )
                }
                CommonDto(
                    code = it.code,
                    message = it.message,
                    response = userInfo,
                )
            }
        }
}
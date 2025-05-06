package com.seoulmate.domain.usecase

import com.seoulmate.database.dao.UserDao
import com.seoulmate.database.model.UserEntity
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userDao: UserDao,
) {

    suspend operator fun invoke(
        userId: Int,
        nickName: String,
        accessToken: String,
        refreshToken: String,
        loginType: String,
    ) {
        userDao.updateUser(
            userId = userId,
            nickName = nickName,
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
}
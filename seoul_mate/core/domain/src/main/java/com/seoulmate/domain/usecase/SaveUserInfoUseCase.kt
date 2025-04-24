package com.seoulmate.domain.usecase

import com.seoulmate.database.dao.UserDao
import com.seoulmate.database.model.UserEntity
import javax.inject.Inject
import kotlin.math.log

class SaveUserInfoUseCase @Inject constructor(
    private val userDao: UserDao,
) {

    suspend operator fun invoke(
        nickName: String,
        accessToken: String,
        refreshToken: String,
        loginType: String,
    ) {
        userDao.upsertUser(
            UserEntity(
                id = 0,
                nickName = nickName,
                accessToken = accessToken,
                refreshToken = refreshToken,
                loginType = loginType,
            )
        )
    }
}
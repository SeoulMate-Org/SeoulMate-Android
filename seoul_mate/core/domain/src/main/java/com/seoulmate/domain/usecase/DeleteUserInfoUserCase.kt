package com.seoulmate.domain.usecase

import com.seoulmate.database.dao.UserDao
import javax.inject.Inject

class DeleteUserInfoUserCase @Inject constructor(
    private val userDao: UserDao,
) {
    suspend operator fun invoke() {
        userDao.deleteUser()
    }
}
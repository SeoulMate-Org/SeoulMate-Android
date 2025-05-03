package com.seoulmate.domain.usecase

import com.seoulmate.data.model.user.UserData
import com.seoulmate.database.dao.UserDao
import com.seoulmate.database.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userDao: UserDao,
) {

    operator fun invoke(): Flow<UserData?> = userDao.getUser().map { it?.asExternalModel() }
}
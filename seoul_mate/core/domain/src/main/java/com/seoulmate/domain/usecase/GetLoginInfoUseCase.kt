package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.LoginDto
import com.seoulmate.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginInfoUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {

    suspend fun getLoginInfo(token: String, loginType: String): Flow<LoginDto?> =
        loginRepository.getLoginInfo(token = token, loginType = loginType)
}
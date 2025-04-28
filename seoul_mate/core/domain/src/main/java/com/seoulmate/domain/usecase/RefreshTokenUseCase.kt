package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.auth.RefreshTokenDto
import com.seoulmate.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {

    suspend operator fun invoke(refreshToken: String): Flow<RefreshTokenDto?>
    = loginRepository.getToken(refreshToken)
}
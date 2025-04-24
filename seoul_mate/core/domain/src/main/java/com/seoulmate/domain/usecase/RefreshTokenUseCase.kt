package com.seoulmate.domain.usecase

import com.seoulmate.data.repository.LoginRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {

    suspend operator fun invoke(refreshToken: String) {
        loginRepository.getToken(refreshToken)
    }
}
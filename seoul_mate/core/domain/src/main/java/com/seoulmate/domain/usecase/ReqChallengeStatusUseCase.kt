package com.seoulmate.domain.usecase

import com.seoulmate.data.repository.ChallengeRepository
import javax.inject.Inject

class ReqChallengeStatusUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    // TODO chan need Common Error Handler

    suspend operator fun invoke(
        id: Int,
        status: String,
    ) = challengeRepository.reqChallengeStatus(
        id = id, status = status )
}
package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.challenge.DeleteChallengeDto
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteChallengeStatusUseCase @Inject constructor(
    val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(
        id: Int
    ): Flow<CommonDto<DeleteChallengeDto?>> = challengeRepository.deleteChallengeStatus(id)

}
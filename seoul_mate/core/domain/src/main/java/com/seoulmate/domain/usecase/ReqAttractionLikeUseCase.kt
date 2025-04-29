package com.seoulmate.domain.usecase

import com.seoulmate.data.repository.ChallengeRepository
import javax.inject.Inject

class ReqAttractionLikeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(
        id: Int,
    ) = challengeRepository.reqAttractionLike(
        id = id,
    )
}
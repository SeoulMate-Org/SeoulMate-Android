package com.seoulmate.domain.usecase

import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReqAttractionStampUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {

    suspend operator fun invoke(
        id: Int,
    ): Flow<Boolean?> = challengeRepository.reqAttractionStamp(
        id = id,
    )

}
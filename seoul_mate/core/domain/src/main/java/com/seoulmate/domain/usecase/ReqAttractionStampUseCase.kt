package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.attraction.AttractionStampDto
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReqAttractionStampUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {

    suspend operator fun invoke(
        id: Int,
    ): Flow<CommonDto<AttractionStampDto?>> = challengeRepository.reqAttractionStamp(
        id = id,
    )

}
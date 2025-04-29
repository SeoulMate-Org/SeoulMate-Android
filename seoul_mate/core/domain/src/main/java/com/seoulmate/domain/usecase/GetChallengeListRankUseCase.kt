package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.challenge.ChallengeRankItemData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChallengeListRankUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(
        language: String,
    ): Flow<CommonDto<List<ChallengeRankItemData>>> = challengeRepository.reqChallengeListRank(
        language = language,
    ).map {
        if (it.response == null) {
            CommonDto(
                code = it.code,
                message = it.message,
                response = listOf(),
            )
        } else {
            val returnResponse = mutableListOf<ChallengeRankItemData>()
            it.response?.let { response ->
                response.forEach { item ->
                    returnResponse.add(
                        ChallengeRankItemData(
                            id = item.id,
                            name = item.name,
                            title = item.title,
                            imageUrl = item.imageUrl,
                            isLiked = item.isLiked,
                            progressCount = item.progressCount,
                        )
                    )
                }
            }
            CommonDto(
                code = it.code,
                message = it.message,
                response = returnResponse.toList(),
            )
        }
    }
}
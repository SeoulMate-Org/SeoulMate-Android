package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.challenge.ChallengeCulturalEventData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChallengeCulturalEventUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(
        language: String
    ): Flow<CommonDto<List<ChallengeCulturalEventData>>> = challengeRepository
        .reqChallengeCulturalEvent(language).map {
            if (it.response == null) {
                CommonDto(
                    code = it.code,
                    message = it.message,
                    response = listOf(),
                )
            } else {
                var returnValue = listOf<ChallengeCulturalEventData>()
                it.response?.let { dataList ->
                    returnValue = dataList.map { data ->
                        ChallengeCulturalEventData(
                            id = data.id,
                            title = data.title ?: "",
                            imageUrl = data.imageUrl ?: "",
                            startDate = data.startDate ?: "",
                            endDate = data.endDate ?: "",
                            isLiked = data.isLiked ?: false,
                        )
                    }
                }

                CommonDto(
                    code = it.code,
                    message = it.message,
                    response = returnValue.toList(),
                )
            }
        }
}
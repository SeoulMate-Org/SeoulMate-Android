package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.challenge.ChallengeMyBadgeData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyBadgeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(
        themeId: Int,
        languageCode: String,
    ): Flow<CommonDto<List<ChallengeMyBadgeData>?>> = challengeRepository.reqMyBadge(
        themeId = themeId,
        languageCode = languageCode,
    ).map {
        if (it.response == null) {
            CommonDto(
                code = it.code,
                message = it.message,
                response = listOf(),
            )
        } else {
            var returnResponse: List<ChallengeMyBadgeData>? = null
            returnResponse = it.response?.let { response ->
                response.map { item ->
                    ChallengeMyBadgeData(
                        themeId = item.themeId,
                        themeName = item.themeName ?: "",
                        themeCount = item.themeCount ?: 0,
                        completeCount = item.completeCount ?: 0,
                        isCompleted = item.isCompleted ?: false,
                    )
                }
            }

            CommonDto(
                code = it.code,
                message = it.message,
                response = returnResponse,
            )
        }
    }

}
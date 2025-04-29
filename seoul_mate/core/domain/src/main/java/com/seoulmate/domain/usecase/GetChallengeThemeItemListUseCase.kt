package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.challenge.ChallengeStampItemData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChallengeThemeItemListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(
        themeId: Int,
        language: String,
    ): Flow<CommonDto<List<ChallengeStampItemData>>> = challengeRepository.reqChallengeListTheme(
        themeId, language
    ).map {
        if (it.response == null) {
            CommonDto(
                code = it.code,
                message = it.message,
                response = listOf(),
            )
        } else {
            val returnResponse = mutableListOf<ChallengeStampItemData>()
            it.response?.let { response ->
                response.forEach { item ->
                    returnResponse.add(
                        ChallengeStampItemData(
                            id = item.id,
                            name = item.name,
                            title = item.title,
                            description = item.description,
                            imageUrl = item.imageUrl,
                            mainLocation = item.mainLocation,
                            challengeThemeId = item.challengeThemeId,
                            challengeThemeName = item.challengeThemeName,
                            likes = item.likes,
                            isLiked = item.isLiked,
                            myStampCount = item.myStampCount,
                            attractionCount = item.attractionCount,
                            commentCount = item.commentCount,
                            distance = item.distance,
                            displayRank = item.displayRank,
                        )
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
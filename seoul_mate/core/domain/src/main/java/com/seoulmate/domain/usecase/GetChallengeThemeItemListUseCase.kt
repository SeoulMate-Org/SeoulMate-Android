package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.challenge.ChallengeThemeItemData
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
    ): Flow<CommonDto<List<ChallengeThemeItemData>>?> = challengeRepository.reqChallengeListTheme(
        themeId, language
    ).map {
        if (it.response == null) {
            CommonDto(
                code = it.code,
                message = it.message,
                response = listOf(),
            )
        } else {
            val returnResponse = mutableListOf<ChallengeThemeItemData>()
            it.response?.let { response ->
                response.forEach { item ->
                    returnResponse.add(
                        ChallengeThemeItemData(
                            id = item.id,
                            name = item.name ?: "",
                            title = item.title ?: "",
                            description = item.description ?: "",
                            imageUrl = item.imageUrl ?: "",
                            mainLocation = item.mainLocation ?: "",
                            challengeThemeId = item.challengeThemeId ?: 0,
                            challengeThemeName = item.challengeThemeName ?: "",
                            likes = item.likes ?: 0,
                            isLiked = item.isLiked ?: false,
                            myStampCount = item.myStampCount ?: 0,
                            attractionCount = item.attractionCount ?: 0,
                            commentCount = item.commentCount ?: 0,
                            distance = item.distance ?: 0,
                            displayRank = item.displayRank ?: "",
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
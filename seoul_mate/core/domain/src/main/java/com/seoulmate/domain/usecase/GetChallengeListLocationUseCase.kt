package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.ChallengeLocationItemData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChallengeListLocationUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(
        locationRequest: MyLocationReqData,
        language: String = "KOR",
    ): Flow<CommonDto<List<ChallengeLocationItemData>>> = challengeRepository
        .reqChallengeListLocation(
            locationRequest, language
        ).map {
            if (it.response == null) {
                CommonDto(
                    code = it.code,
                    message = it.message,
                    response = listOf(),
                )
            } else {
                val returnResponse = mutableListOf<ChallengeLocationItemData>()
                it.response?.let { response ->
                    response.forEach { item ->
                        returnResponse.add(
                            ChallengeLocationItemData(
                                id = item.id,
                                name = item.name,
                                title = item.title,
                                description = item.description,
                                imageUrl = item.imageUrl,
                                mainLocation = item.mainLocation,
                                challengeThemeId = item.challengeThemeId,
                                challengeThemeName = item.challengeThemeName,
                                isLiked = item.isLiked,
                                myStampCount = item.myStampCount,
                                progressCount = null,
                                attractionCount = item.attractionCount,
                                commentCount = item.commentCount,
                                distance = item.distance,
                                likes = item.likes,
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
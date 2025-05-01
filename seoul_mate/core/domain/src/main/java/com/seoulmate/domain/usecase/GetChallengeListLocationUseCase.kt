package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.challenge.ChallengeLocationData
import com.seoulmate.data.model.challenge.ChallengeLocationItemData
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
    ): Flow<CommonDto<ChallengeLocationItemData>> = challengeRepository
        .reqChallengeListLocation(
            locationRequest, language
        ).map {
            if (it.response == null) {
                CommonDto(
                    code = it.code,
                    message = it.message,
                    response = null,
                )
            } else {
                var returnResponse: ChallengeLocationItemData? = null
                it.response?.let { response ->
                    returnResponse = ChallengeLocationItemData(
                        jongGak = response.jongGak ?: false,
                        challenges = response.challenges.map {
                            ChallengeLocationData(
                                id = it.id,
                                name = it.name ?: "",
                                title = it.title ?: "",
                                description = it.description ?: "",
                                imageUrl = it.imageUrl ?: "",
                                mainLocation = it.mainLocation ?: "",
                                challengeThemeId = it.challengeThemeId ?: 0,
                                challengeThemeName = it.challengeThemeName ?: "",
                                isLiked = it.isLiked ?: false,
                                likes = it.likes ?: 0,
                                myStampCount = it.myStampCount ?: 0,
                                attractionCount = it.attractionCount ?: 0,
                                commentCount = it.commentCount ?: 0,
                                distance = it.distance ?: 0,
                                displayRank = it.displayRank ?: "",
                                level = it.level ?: "",
                            )
                        },
                    )
                }
                CommonDto(
                    code = it.code,
                    message = it.message,
                    response = returnResponse,
                )
            }
        }
}
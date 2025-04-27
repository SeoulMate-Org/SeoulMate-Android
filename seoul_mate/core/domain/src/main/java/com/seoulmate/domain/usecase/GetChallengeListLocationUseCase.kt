package com.seoulmate.domain.usecase

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
    ): Flow<List<ChallengeLocationItemData>?> = challengeRepository
        .reqChallengeListLocation(
            locationRequest, language
        ).map {
            if (it.code in 200..299) {
                it.response?.let { response ->
                    response.map { item ->
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
                            progressCount = item.progressCount,
                            attractionCount = item.attractionCount,
                            commentCount = item.commentCount,
                            distance = item.distance,
                        )
                    }
                }
            } else {
                null
            }
        }
}
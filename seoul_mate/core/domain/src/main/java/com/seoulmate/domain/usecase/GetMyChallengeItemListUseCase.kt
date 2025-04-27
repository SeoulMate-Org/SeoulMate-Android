package com.seoulmate.domain.usecase

import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyChallengeItemListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {

    suspend operator fun invoke(
        type: String,
        language: String = "KOR",
    ): Flow<List<MyChallengeItemData>> = challengeRepository.getMyChallengeList(
        type = type,
        language = language,
    ).map { response ->
        val myChallengeItemList = mutableListOf<MyChallengeItemData>()
        response?.let {
            it.forEach { contentItem ->
                myChallengeItemList.add(
                    MyChallengeItemData(
                        id = contentItem.id,
                        name = contentItem.name,
                        title = contentItem.title,
                        likes = contentItem.likes,
                        attractionCount = contentItem.attractionCount,
                        myStampCount = contentItem.myStampCount,
                        mainLocation = contentItem.mainLocation,
                        challengeThemeId = contentItem.challengeThemeId,
                        challengeThemeName = contentItem.challengeThemeName,
                    )
                )
            }
        }
        return@map myChallengeItemList
    }

}
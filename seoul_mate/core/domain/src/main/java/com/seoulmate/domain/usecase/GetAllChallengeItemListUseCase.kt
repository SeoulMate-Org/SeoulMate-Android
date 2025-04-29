package com.seoulmate.domain.usecase

import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllChallengeItemListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {

    suspend operator fun invoke(
        page: Int,
        size: Int,
        keyword: String = "",
    ): Flow<List<ChallengeItemData>> =
        challengeRepository.getChallengeItemAll(
            page = page,
            size = size,
            keyword = keyword,
        ).map { response ->
            val challengeItemList = mutableListOf<ChallengeItemData>()
            response?.let {
                it.content.forEach { contentItem ->
                    challengeItemList.add(
                        ChallengeItemData(
                            id = contentItem.id,
                            themeList = listOf(contentItem.challengeThemeId),
                            title = contentItem.title,
                            titleEng = contentItem.titleEng,
                            description = contentItem.description,
                            descriptionEng = contentItem.descriptionEng,
                            attractionIdList = contentItem.attractionIdList,
                            mainLocation = contentItem.mainLocation,
                            imgUrl = ""
                        )
                    )
                }
            }
            return@map challengeItemList
        }

}
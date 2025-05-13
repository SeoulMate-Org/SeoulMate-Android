package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.attraction.AttractionDetailData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyAttractionListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(
        language: String,
    ): Flow<CommonDto<List<AttractionDetailData>>> = challengeRepository.reqAttractionMy(
        language = language,
    ).map {
        if (it.response == null) {
            CommonDto(
                code = it.code,
                message = it.message,
                response = listOf(),
            )
        } else {
            var returnValue: List<AttractionDetailData> = listOf()

            it.response?.let {
                returnValue = it.map { item ->
                    AttractionDetailData(
                        id = item.id,
                        name = item.name ?: "",
                        description = item.description ?: "",
                        detailCodes = item.detailCodes,
                        address = item.address ?: "",
                        businessHours = item.businessHours ?: "",
                        homepageUrl = item.homepageUrl ?: "",
                        locationX = item.locationX ?: "",
                        locationY = item.locationY ?: "",
                        tel = item.tel ?: "",
                        subway = item.subway ?: "",
                        imageUrl = item.imageUrl ?: "",
                        likes = item.likes ?: 0 ,
                        isLiked = item.isLiked ?: false,
                        stampCount = item.stampCount,
                    )
                }
            }

            CommonDto(
                code = it.code,
                message = it.message,
                response = returnValue,
            )
        }
    }
}
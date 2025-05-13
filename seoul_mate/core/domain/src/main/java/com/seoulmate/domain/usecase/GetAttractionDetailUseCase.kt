package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.attraction.AttractionDetailData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAttractionDetailUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(
        id: Int, language: String
    ): Flow<CommonDto<AttractionDetailData>> = challengeRepository
        .reqAttractionDetail(id = id, language = language)
        .map {
            if (it.response == null) {
                CommonDto(
                    code = it.code,
                    message = it.message,
                    response = null,
                )
            } else {
                var returnValue: AttractionDetailData? = null
                it.response?.let {
                    returnValue = AttractionDetailData(
                        id = it.id,
                        name = it.name ?: "",
                        description = it.description ?: "",
                        detailCodes = it.detailCodes,
                        address = it.address ?: "",
                        businessHours = it.businessHours ?: "",
                        homepageUrl = it.homepageUrl ?: "",
                        locationX = it.locationX ?: "",
                        locationY = it.locationY ?: "",
                        tel = it.tel ?: "",
                        subway = it.subway ?: "",
                        imageUrl = it.imageUrl ?: "",
                        likes = it.likes ?: 0,
                        isLiked = it.isLiked?: false,
                        stampCount = it.stampCount,
                    )
                }
                CommonDto(
                    code = it.code,
                    message = it.message,
                    response = returnValue,
                )
            }
        }
}
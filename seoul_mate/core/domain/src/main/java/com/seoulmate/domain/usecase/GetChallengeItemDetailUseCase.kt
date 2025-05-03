package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.challenge.AttractionItem
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChallengeItemDetailUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {

    suspend operator fun invoke(
        id: Int,
        language: String = "KOR",
    ): Flow<CommonDto<ChallengeItemData?>> = challengeRepository.getChallengeItemDetail(
        id = id,
        language = language
    ).map {
        if (it.response == null) {
            CommonDto(
                code = it.code,
                message = it.message,
                response = null,
            )
        } else {
            var returnResponse: ChallengeItemData? = null
            it.response?.let { response ->
                val attractions = mutableListOf<AttractionItem>()
                response.attractions.forEach { attractionItem ->
                    attractions.add(
                        AttractionItem(
                            id = attractionItem.id,
                            name = attractionItem.name,
                            locationX = attractionItem.locationX,
                            locationY = attractionItem.locationY,
                            isLiked = attractionItem.isLiked,
                            likes = attractionItem.likes,
                            isStamped = attractionItem.isStamped,
                            stampCount = attractionItem.stampCount,
                            address = attractionItem.address,
                            imageUrl = attractionItem.imageUrl ?: "",
                        )
                    )
                }

                returnResponse = ChallengeItemData(
                    id = response.id,
                    title = response.title,
                    name = response.name,
                    description = response.description,
                    imgUrl = response.imageUrl,
                    attractions = attractions,
                    isInterest = response.isLiked ?: false,
                    mainLocation = response.mainLocation,
                    likedCount = response.likedCount,
                    progressCount = response.progressCount,
                    attractionCount = response.attractionCount,
                    commentCount = response.commentCount,
                    comments = response.comments.map {
                            commentItem ->
                        ChallengeCommentItem(
                            id = commentItem.commentId,
                            comment = commentItem.comment,
                            createdAt = commentItem.createdAt,
                            modifiedAt = "",
                            nickname = commentItem.nickname,
                            isMine = commentItem.isMine,
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
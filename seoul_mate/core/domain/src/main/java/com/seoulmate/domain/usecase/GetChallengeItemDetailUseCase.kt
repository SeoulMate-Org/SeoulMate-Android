package com.seoulmate.domain.usecase

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
    ): Flow<ChallengeItemData?> = challengeRepository.getChallengeItemDetail(
        id = id,
        language = language
    ).map { response ->
        response?.let {
            val attractions = mutableListOf<AttractionItem>()
            it.attractions.forEach { attractionItem ->
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
                    )
                )
            }

            ChallengeItemData(
                id = it.id,
                title = it.title,
                description = it.description,
                imgUrl = "",
                attractions = attractions,
                isInterest = it.isLiked ?: false,
                mainLocation = it.mainLocation,
                likedCount = it.likedCount,
                progressCount = it.progressCount,
                attractionCount = it.attractionCount,
                commentCount = it.commentCount,
                comments = it.comments.map {
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
    }
}
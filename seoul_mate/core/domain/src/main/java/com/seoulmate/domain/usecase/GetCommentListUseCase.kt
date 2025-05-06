package com.seoulmate.domain.usecase

import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCommentListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {

    suspend operator fun invoke(
        id: Int,
        languageCode: String,
    ): Flow<List<ChallengeCommentItem>?> = challengeRepository.reqCommentList(
        id = id, languageCode
    ).map {
        if (it.code in 200..299) {
            it.response?.let { response ->
                response.map { contentItem ->
                    ChallengeCommentItem(
                        id = contentItem.commentId,
                        comment = contentItem.comment,
                        createdAt = contentItem.createdAt,
                        modifiedAt = "",
                        isMine = contentItem.isMine,
                        nickname = contentItem.nickname,
                    )
                }
            }
        } else {
            null
        }
    }
}
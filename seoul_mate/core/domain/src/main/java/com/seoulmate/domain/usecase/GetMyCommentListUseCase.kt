package com.seoulmate.domain.usecase

import com.seoulmate.data.model.ChallengeCommentItem
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyCommentListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    operator fun invoke(
        languageCode: String,
    ): Flow<List<ChallengeCommentItem>?> = flow {
        challengeRepository.reqMyCommentLisT(
            languageCode
        ).map {
            if (it.code in 200..299) {
                it.response?.let { response ->
                    response.content.map { contentItem ->
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
}
package com.seoulmate.domain.usecase

import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(
        commentId: Int
    ): Flow<Boolean?> = challengeRepository.deleteComment(commentId)
        .map { it.response != null && it.code in 200..299 }

}
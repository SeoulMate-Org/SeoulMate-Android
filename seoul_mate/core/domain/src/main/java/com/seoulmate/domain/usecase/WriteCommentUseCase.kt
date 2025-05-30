package com.seoulmate.domain.usecase

import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WriteCommentUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {

    suspend operator fun invoke(
        id: Int,
        comment: String,
        languageCode: String,
    ): Flow<Boolean?> = challengeRepository.writeComment(
        id = id,
        comment = comment,
        languageCode = languageCode,
    ).map { it.response != null && it.code in 200..299 }
}
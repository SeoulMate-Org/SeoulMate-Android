package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMyCommentListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {

    suspend operator fun invoke(
        language: String,
    ): Flow<CommonDto<List<ChallengeCommentItem>>> = challengeRepository.reqMyCommentList(
        language
    ).map {
        val returnResponse = if (it.response == null) {
            CommonDto(
                code = it.code,
                message = it.message,
                response = listOf(),
            )
        } else {
            val returnResponse = mutableListOf<ChallengeCommentItem>()
            it.response?.let { response ->
                response.forEach { contentItem ->
                    returnResponse.add(
                        ChallengeCommentItem(
                            id = contentItem.commentId,
                            comment = contentItem.comment,
                            createdAt = contentItem.createdAt,
                            modifiedAt = "",
                            isMine = contentItem.isMine,
                            nickname = contentItem.nickname,
                        )
                    )
                }
            }
            CommonDto(
                code = it.code,
                message = it.message,
                response = returnResponse.toList(),
            )
        }
        return@map returnResponse
    }
}
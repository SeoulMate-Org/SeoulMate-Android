package com.seoulmate.data.repository.impl

import com.seoulmate.data.api.ApiService
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.challenge.ChallengeItemAllDto
import com.seoulmate.data.dto.challenge.ChallengeItemDetailDto
import com.seoulmate.data.dto.challenge.ChallengeItemLikeDto
import com.seoulmate.data.dto.challenge.ChallengeLocationItemDto
import com.seoulmate.data.dto.challenge.ChallengeStatusDto
import com.seoulmate.data.dto.challenge.ChallengeThemeDto
import com.seoulmate.data.dto.challenge.MyChallengeDto
import com.seoulmate.data.dto.comment.CommentDto
import com.seoulmate.data.dto.comment.WriteCommentDto
import com.seoulmate.data.model.request.AttractionStampReqData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.model.request.WriteCommentReqData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
): ChallengeRepository {

    override suspend fun getChallengeItemAll(
        page: Int,
        size: Int,
        keyword: String,
    ): Flow<ChallengeItemAllDto?> = flow {
        val response = apiService.reqChallengeItemAll(
            page = page,
            size = size,
            keyword = keyword,
        )
        emit(response.body())
    }

    override suspend fun getChallengeItemDetail(
        id: Int,
        language: String
    ): Flow<ChallengeItemDetailDto?> = flow {
        val response = apiService.reqChallengeItemDetail(
            id = id,
            language = language,
        )
        emit(response.body())
    }

    override suspend fun getChallengeTheme(): Flow<List<ChallengeThemeDto>?> = flow {
        val response = apiService.reqChallengeTheme()
        emit(response.body())
    }

    override suspend fun getMyChallengeList(
        type: String,
        language: String,
    ): Flow<List<MyChallengeDto>?> = flow {
        val response = apiService.reqMyChallenge(
            myChallenge = type,
            language = language,
        )
        emit(response.body())
    }

    override suspend fun reqChallengeStatus(
        id: Int, status: String
    ): Flow<ChallengeStatusDto?> = flow {
        val response = apiService.reqChallengeStatus(
            id = id,
            status = status,
        )
        emit(response.body())
    }

    override suspend fun reqChallengeLike(
        id: Int
    ): Flow<ChallengeItemLikeDto?> = flow {
        val response = apiService.reqChallengeLike(
            id = id,
        )
        emit(response.body())
    }

    override suspend fun reqChallengeListLocation(
        locationRequest: MyLocationReqData,
        language: String
    ): Flow<CommonDto<List<ChallengeLocationItemDto>?>> = flow {
        val response = apiService.reqChallengeListLocation(
            locationRequest = locationRequest,
            language = language,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }


    override suspend fun reqAttractionStamp(
        id: Int
    ): Flow<Boolean?> = flow {
//        val response = apiService.reqAttractionStamp(
//            id = id,
//        )
        val response = apiService.reqAttractionStampTest(
            AttractionStampReqData(id)
        )
        emit(response.code() in 200..299)
    }

    override suspend fun writeComment(
        id: Int,
        comment: String,
        languageCode: String,
    ): Flow<CommonDto<WriteCommentDto?>> = flow {
        val response = apiService.writeComment(
            WriteCommentReqData(
                challengeId = id,
                comment = comment,
                languageCode = languageCode,
            )
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }

    override suspend fun reqCommentList(
        id: Int,
        languageCode: String
    ): Flow<CommonDto<CommentDto>> = flow {
        val response = apiService.reqCommentList(
            id = id,
            languageCode = languageCode,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }

    override suspend fun reqMyCommentList(
        language: String
    ): Flow<CommonDto<CommentDto>> = flow {
        val response = apiService.reqMyCommentList(
            language = language,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body()
            )
        )
    }

}
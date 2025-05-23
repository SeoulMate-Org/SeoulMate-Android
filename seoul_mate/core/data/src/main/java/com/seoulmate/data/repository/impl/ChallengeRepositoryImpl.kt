package com.seoulmate.data.repository.impl

import com.seoulmate.data.api.ApiService
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.attraction.AttractionDetailDto
import com.seoulmate.data.dto.attraction.AttractionStampDto
import com.seoulmate.data.dto.challenge.ChallengeCulturalEventDto
import com.seoulmate.data.dto.challenge.ChallengeItemAllDto
import com.seoulmate.data.dto.challenge.ChallengeItemDetailDto
import com.seoulmate.data.dto.challenge.ChallengeItemLikeDto
import com.seoulmate.data.dto.challenge.ChallengeLocationItemDto
import com.seoulmate.data.dto.challenge.ChallengeMyBadgeDto
import com.seoulmate.data.dto.challenge.ChallengeRankItemDto
import com.seoulmate.data.dto.challenge.ChallengeStampItemDto
import com.seoulmate.data.dto.challenge.ChallengeStatusDto
import com.seoulmate.data.dto.challenge.ChallengeThemeDto
import com.seoulmate.data.dto.challenge.ChallengeThemeItemDto
import com.seoulmate.data.dto.challenge.DeleteChallengeDto
import com.seoulmate.data.dto.challenge.MyChallengeDto
import com.seoulmate.data.dto.comment.CommentContentDto
import com.seoulmate.data.dto.comment.CommentDto
import com.seoulmate.data.dto.comment.DeleteCommentDto
import com.seoulmate.data.dto.comment.WriteCommentDto
import com.seoulmate.data.model.request.AttractionStampReqData
import com.seoulmate.data.model.request.ModifyCommentReqData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.model.request.WriteCommentReqData
import com.seoulmate.data.model.request.toJson
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
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
    ): Flow<CommonDto<ChallengeItemDetailDto?>> = flow {
        val response = apiService.reqChallengeItemDetail(
            id = id,
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

    override suspend fun getChallengeTheme(): Flow<List<ChallengeThemeDto>?> = flow {
        val response = apiService.reqChallengeTheme()
        emit(response.body())
    }

    override suspend fun getMyChallengeList(
        type: String,
        language: String,
    ): Flow<CommonDto<List<MyChallengeDto>?>> = flow {
        val response = apiService.reqMyChallenge(
            myChallenge = type,
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

    override suspend fun reqChallengeStatus(
        id: Int, status: String
    ): Flow<CommonDto<ChallengeStatusDto?>> = flow {
        val response = apiService.reqChallengeStatus(
            id = id,
            status = status,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }

    override suspend fun deleteChallengeStatus(
        id: Int
    ): Flow<CommonDto<DeleteChallengeDto?>> = flow {
        val response = apiService.deleteChallengeStatus(
            id = id,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }

    override suspend fun reqChallengeLike(
        id: Int
    ): Flow<CommonDto<ChallengeItemLikeDto>> = flow {
        val response = apiService.reqChallengeLike(
            id = id,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }

    override suspend fun reqChallengeListLocation(
        locationRequest: MyLocationReqData,
        language: String
    ): Flow<CommonDto<ChallengeLocationItemDto?>> = flow {
        val response = apiService.reqChallengeListLocation(
            locationX = locationRequest.locationX,
            locationY = locationRequest.locationY,
            limit = locationRequest.limit,
            radius = locationRequest.radius,
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

    override suspend fun reqChallengeListStamp(
        attractionId: Int?,
        language: String
    ): Flow<CommonDto<ChallengeStampItemDto?>> = flow {
        val response = apiService.reqChallengeListStamp(
            attractionId = attractionId,
            language = language,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),))
    }

    override suspend fun reqChallengeListTheme(
        themeId: Int,
        language: String
    ): Flow<CommonDto<List<ChallengeThemeItemDto>?>> = flow {
        val response = apiService.reqChallengeListTheme(
            themeId = themeId,
            language = language,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),))
    }

    override suspend fun reqChallengeListRank(
        language: String
    ): Flow<CommonDto<List<ChallengeRankItemDto>?>> = flow {
        val response = apiService.reqChallengeListRank(
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

    override suspend fun reqChallengeCulturalEvent(
        language: String
    ): Flow<CommonDto<List<ChallengeCulturalEventDto>?>> = flow {
        val response = apiService.reqChallengeListCulturalEvent(
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

    override suspend fun reqChallengeSeoulMaster(
        language: String
    ): Flow<CommonDto<List<ChallengeCulturalEventDto>?>> = flow {
        val response = apiService.reqChallengeListSeoulMaster(
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
    ): Flow<CommonDto<AttractionStampDto?>> = flow {
//        val response = apiService.reqAttractionStamp(
//            id = id,
//        )
        val response = apiService.reqAttractionStamp(
//            AttractionStampReqData(id)
            id = id,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }

    override suspend fun reqAttractionDetail(
        id: Int,
        language: String
    ): Flow<CommonDto<AttractionDetailDto>> = flow {
        val response = apiService.reqAttractionDetail(
            id = id,
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

    override suspend fun reqAttractionMy(
        language: String
    ): Flow<CommonDto<List<AttractionDetailDto>>> = flow {
        val response = apiService.reqAttractionMy(
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

    override suspend fun reqAttractionLike(
        id: Int
    ): Flow<CommonDto<ChallengeItemLikeDto>> = flow {
        val response = apiService.reqAttractionLike(
            id = id,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
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

    override suspend fun modifyComment(
        commentId: Int,
        comment: String,
        languageCode: String
    ): Flow<CommonDto<WriteCommentDto?>> = flow {
        val response = apiService.modifyComment(
            ModifyCommentReqData(
                commentId = commentId,
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

    override suspend fun deleteComment(commentId: Int): Flow<CommonDto<DeleteCommentDto?>> = flow {
        val response = apiService.deleteComment(
            id = commentId,
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
        language: String
    ): Flow<CommonDto<List<CommentContentDto>>> = flow {
        val response = apiService.reqCommentList(
            id = id,
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

    override suspend fun reqMyCommentList(
        language: String
    ): Flow<CommonDto<List<CommentContentDto>>> = flow {
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

    override suspend fun reqMyBadge(
        themeId: Int,
        languageCode: String
    ): Flow<CommonDto<List<ChallengeMyBadgeDto>>> = flow {
        val response = apiService.reqChallengeMyBadge(
            themeId = themeId,
            language = languageCode,
        )
        emit(
            CommonDto(
                code = response.code(),
                message = response.message(),
                response = response.body(),
            )
        )
    }

}
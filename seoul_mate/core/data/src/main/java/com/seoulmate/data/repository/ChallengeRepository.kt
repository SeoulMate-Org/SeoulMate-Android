package com.seoulmate.data.repository

import com.google.android.gms.common.internal.service.Common
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
import com.seoulmate.data.model.request.MyLocationReqData
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    // fetch challenge item all
    suspend fun getChallengeItemAll(
        page: Int,
        size: Int,
        keyword: String = "",
    ): Flow<ChallengeItemAllDto?>

    // fetch challenge item detail
    suspend fun getChallengeItemDetail(
        id: Int,
        language: String = "KOR",
    ): Flow<CommonDto<ChallengeItemDetailDto?>>

    // fetch challenge theme item list
    suspend fun getChallengeTheme(): Flow<List<ChallengeThemeDto>?>

    // fetch my challenge item list
    suspend fun getMyChallengeList(
        type: String,
        language: String = "KOR",
    ): Flow<CommonDto<List<MyChallengeDto>?>>

    // put challenge status
    suspend fun reqChallengeStatus(
        id: Int,
        status: String, // Available values : PROGRESS, COMPLETE
    ): Flow<CommonDto<ChallengeStatusDto?>>

    // delete challenge status
    suspend fun deleteChallengeStatus(
        id: Int,
    ): Flow<CommonDto<DeleteChallengeDto?>>

    // put Challenge Like
    suspend fun reqChallengeLike(
        id: Int,
    ): Flow<CommonDto<ChallengeItemLikeDto>>

    // fetch challenge list location
    suspend fun reqChallengeListLocation(
        locationRequest: MyLocationReqData,
        language: String,
    ): Flow<CommonDto<ChallengeLocationItemDto?>>

    // fetch challenge list stamp
    suspend fun reqChallengeListStamp(
        attractionId: Int?,
        language: String,
    ): Flow<CommonDto<ChallengeStampItemDto?>>

    // fetch challenge list theme
    suspend fun reqChallengeListTheme(
        themeId: Int,
        language: String,
    ): Flow<CommonDto<List<ChallengeThemeItemDto>?>>

    // fetch challenge list rank
    suspend fun reqChallengeListRank(
        language: String,
    ): Flow<CommonDto<List<ChallengeRankItemDto>?>>

    // fetch challenge cultural event
    suspend fun reqChallengeCulturalEvent(
        language: String,
    ): Flow<CommonDto<List<ChallengeCulturalEventDto>?>>

    // fetch challenge seoul master
    suspend fun reqChallengeSeoulMaster(
        language: String,
    ): Flow<CommonDto<List<ChallengeCulturalEventDto>?>>

    // fetch attraction stamp
    suspend fun reqAttractionStamp(
        id: Int,
    ): Flow<CommonDto<AttractionStampDto?>>

    // fetch attraction detail
    suspend fun reqAttractionDetail(
        id: Int,
        language: String,
    ): Flow<CommonDto<AttractionDetailDto>>

    // fetch attraction my
    suspend fun reqAttractionMy(
        language: String,
    ): Flow<CommonDto<List<AttractionDetailDto>>>

    // put attraction like
    suspend fun reqAttractionLike(
        id: Int,
    ): Flow<CommonDto<ChallengeItemLikeDto>>

    // write comments
    suspend fun writeComment(
        id: Int,
        comment: String,
        languageCode: String,
    ): Flow<CommonDto<WriteCommentDto?>>

    // modify comments
    suspend fun modifyComment(
        commentId: Int,
        comment: String,
        languageCode: String,
    ): Flow<CommonDto<WriteCommentDto?>>

    // delete comments
    suspend fun deleteComment(
        commentId: Int,
    ): Flow<CommonDto<DeleteCommentDto?>>

    // fetch comments list
    suspend fun reqCommentList(
        id: Int,
        language: String,
    ): Flow<CommonDto<List<CommentContentDto>>>

    // fetch my comments list
    suspend fun reqMyCommentList(
        language: String,
    ): Flow<CommonDto<List<CommentContentDto>>>

    // fetch challenge my badge
    suspend fun reqMyBadge(
        themeId: Int,
        languageCode: String,
    ): Flow<CommonDto<List<ChallengeMyBadgeDto>>>

}
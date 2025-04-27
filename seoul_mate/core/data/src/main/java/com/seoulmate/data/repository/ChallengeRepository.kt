package com.seoulmate.data.repository

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
    ): Flow<ChallengeItemDetailDto?>

    // fetch challenge theme item list
    suspend fun getChallengeTheme(): Flow<List<ChallengeThemeDto>?>

    // fetch my challenge item list
    suspend fun getMyChallengeList(
        type: String,
        language: String = "KOR",
    ): Flow<List<MyChallengeDto>?>

    // put challenge status
    suspend fun reqChallengeStatus(
        id: Int,
        status: String, // Available values : PROGRESS, COMPLETE
    ): Flow<ChallengeStatusDto?>

    // put Challenge Like
    suspend fun reqChallengeLike(
        id: Int,
    ): Flow<ChallengeItemLikeDto?>

    // fetch challenge list location
    suspend fun reqChallengeListLocation(
        locationRequest: MyLocationReqData,
        language: String = "KOR",
    ): Flow<CommonDto<List<ChallengeLocationItemDto>?>>

    // fetch attraction stamp
    suspend fun reqAttractionStamp(
        id: Int,
    ): Flow<Boolean?>

    // write comments
    suspend fun writeComment(
        id: Int,
        comment: String,
        languageCode: String,
    ): Flow<CommonDto<WriteCommentDto?>>

    // fetch comments list
    suspend fun reqCommentList(
        id: Int,
        languageCode: String,
    ): Flow<CommonDto<CommentDto>>

    // fetch my comments list
    suspend fun reqMyCommentList(
        language: String,
    ): Flow<CommonDto<CommentDto>>

}
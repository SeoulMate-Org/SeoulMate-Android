package com.seoulmate.data.api

import com.seoulmate.data.dto.attraction.AttractionStampDto
import com.seoulmate.data.dto.challenge.ChallengeItemAllDto
import com.seoulmate.data.dto.challenge.ChallengeItemDetailDto
import com.seoulmate.data.dto.challenge.ChallengeItemLikeDto
import com.seoulmate.data.dto.challenge.ChallengeLocationItemDto
import com.seoulmate.data.dto.challenge.ChallengeStampItemDto
import com.seoulmate.data.dto.challenge.ChallengeStatusDto
import com.seoulmate.data.dto.challenge.ChallengeThemeDto
import com.seoulmate.data.dto.challenge.MyChallengeDto
import com.seoulmate.data.dto.comment.CommentContentDto
import com.seoulmate.data.dto.comment.CommentDto
import com.seoulmate.data.dto.comment.WriteCommentDto
import com.seoulmate.data.model.request.AttractionStampReqData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.model.request.WriteCommentReqData
import com.seoulmate.data.utils.NetworkConfig
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Challenge
    @GET(NetworkConfig.Service.CHALLENGE)
    suspend fun reqChallengeItemAll(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("keyword") keyword: String,
    ): Response<ChallengeItemAllDto>

    @GET(NetworkConfig.Service.CHALLENGE + "/{id}")
    suspend fun reqChallengeItemDetail(
        @Path("id") id: Int,
        @Query("language") language: String,
    ): Response<ChallengeItemDetailDto>

    @GET(NetworkConfig.Service.CHALLENGE_THEME)
    suspend fun reqChallengeTheme(): Response<List<ChallengeThemeDto>>

    @GET(NetworkConfig.Service.CHALLENGE_MY)
    suspend fun reqMyChallenge(
        @Query("myChallenge") myChallenge: String, // Available values : LIKE, PROGRESS, COMPLETE
        @Query("language") language: String,
    ): Response<List<MyChallengeDto>>

    @PUT(NetworkConfig.Service.CHALLENGE_STATUS)
    suspend fun reqChallengeStatus(
        @Query("id") id: Int,
        @Query("status") status: String, // Available values :  PROGRESS, COMPLETE
    ): Response<ChallengeStatusDto>

    @PUT(NetworkConfig.Service.CHALLENGE_LIKE)
    suspend fun reqChallengeLike(
        @Query("id") id: Int,
    ): Response<ChallengeItemLikeDto?>

    @GET(NetworkConfig.Service.CHALLENGE_LIST_LOCATION)
    suspend fun reqChallengeListLocation(
        @Query("locationX") locationX: Double,
        @Query("locationY") locationY: Double,
        @Query("radius") radius: Int,
        @Query("limit") limit: Int,
        @Query("language") language: String,
    ): Response<List<ChallengeLocationItemDto>?>

    @GET(NetworkConfig.Service.CHALLENGE_LIST_STAMP)
    suspend fun reqChallengeListStamp(
        @Query("attractionId") attractionId: Int?,
        @Query("language") language: String,
    ): Response<List<ChallengeStampItemDto>?>

    @GET(NetworkConfig.Service.CHALLENGE_LIST_THEME)
    suspend fun reqChallengeListTheme(
        @Query("themeId") themeId: Int,
        @Query("language") language: String,
    ): Response<List<ChallengeStampItemDto>?>

    // Attraction
    @POST(NetworkConfig.Service.ATTRACTION_STAMP)
    suspend fun reqAttractionStamp(
        @Query("id") id: Int,
    ): Response<AttractionStampDto?>

    @POST(NetworkConfig.Service.ATTRACTION_STAMP)
    suspend fun reqAttractionStampTest(
        @Body body: AttractionStampReqData
    ): Response<AttractionStampDto?>

    // Comment
    @POST(NetworkConfig.Service.COMMENT)
    suspend fun writeComment(
        @Body body: WriteCommentReqData,
    ): Response<WriteCommentDto>

    @GET(NetworkConfig.Service.COMMENT + "/{id}")
    suspend fun reqCommentList(
        @Path("id") id: Int,
        @Query("languageCode") languageCode: String,
    ): Response<CommentDto>

    @GET(NetworkConfig.Service.COMMENT_MY)
    suspend fun reqMyCommentList(
        @Query("language") language: String,
    ): Response<List<CommentContentDto>>

}
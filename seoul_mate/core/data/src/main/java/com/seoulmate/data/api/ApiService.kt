package com.seoulmate.data.api

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
import com.seoulmate.data.dto.challenge.MyChallengeDto
import com.seoulmate.data.dto.comment.CommentContentDto
import com.seoulmate.data.dto.comment.CommentDto
import com.seoulmate.data.dto.comment.DeleteCommentDto
import com.seoulmate.data.dto.comment.WriteCommentDto
import com.seoulmate.data.dto.user.DeleteUserDto
import com.seoulmate.data.dto.user.UserInfoDto
import com.seoulmate.data.dto.user.UserNicknameDto
import com.seoulmate.data.model.request.AttractionStampReqData
import com.seoulmate.data.model.request.ModifyCommentReqData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.model.request.WriteCommentReqData
import com.seoulmate.data.utils.NetworkConfig
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    ): Response<ChallengeLocationItemDto?>

    @GET(NetworkConfig.Service.CHALLENGE_LIST_STAMP)
    suspend fun reqChallengeListStamp(
        @Query("attractionId") attractionId: Int?,
        @Query("language") language: String,
    ): Response<ChallengeStampItemDto?>

    @GET(NetworkConfig.Service.CHALLENGE_LIST_THEME)
    suspend fun reqChallengeListTheme(
        @Query("themeId") themeId: Int,
        @Query("language") language: String,
    ): Response<List<ChallengeThemeItemDto>?>

    @GET(NetworkConfig.Service.CHALLENGE_LIST_RANK)
    suspend fun reqChallengeListRank(
        @Query("language") language: String,
    ): Response<List<ChallengeRankItemDto>?>

    @GET(NetworkConfig.Service.CHALLENGE_LIST_CULTURAL_EVENT)
    suspend fun reqChallengeListCulturalEvent(
        @Query("language") language: String,
    ): Response<List<ChallengeCulturalEventDto>?>

    @GET(NetworkConfig.Service.CHALLENGE_LIST_SEOUL_MASTER)
    suspend fun reqChallengeListSeoulMaster(
        @Query("language") language: String,
    ): Response<List<ChallengeCulturalEventDto>?>

    @GET(NetworkConfig.Service.CHALLENGE_MY_BADGE)
    suspend fun reqChallengeMyBadge(
        @Query("themeId") themeId: Int,
        @Query("language") language: String,
    ): Response<List<ChallengeMyBadgeDto>>

    // Attraction
    @POST(NetworkConfig.Service.ATTRACTION_STAMP)
    suspend fun reqAttractionStamp(
        @Query("id") id: Int,
    ): Response<AttractionStampDto?>

    @GET(NetworkConfig.Service.ATTRACTION + "/{id}")
    suspend fun reqAttractionDetail(
        @Path ("id") id: Int,
        @Query("language") language: String,
    ): Response<AttractionDetailDto?>

    @PUT(NetworkConfig.Service.ATTRACTION_LIKE)
    suspend fun reqAttractionLike(
        @Query("id") id: Int,
    ): Response<ChallengeItemLikeDto?>

    @GET(NetworkConfig.Service.ATTRACTION_MY)
    suspend fun reqAttractionMy(
        @Query("language") language: String,
    ): Response<List<AttractionDetailDto>>

    // Comment
    @POST(NetworkConfig.Service.COMMENT)
    suspend fun writeComment(
        @Body body: WriteCommentReqData,
    ): Response<WriteCommentDto>

    @PUT(NetworkConfig.Service.COMMENT)
    suspend fun modifyComment(
        @Body body: ModifyCommentReqData,
    ): Response<WriteCommentDto>

    @GET(NetworkConfig.Service.COMMENT + "/{id}")
    suspend fun reqCommentList(
        @Path("id") id: Int,
        @Query("language") language: String,
    ): Response<List<CommentContentDto>>

    @DELETE(NetworkConfig.Service.COMMENT + "/{id}")
    suspend fun deleteComment(
        @Path("id") id: Int,
    ): Response<DeleteCommentDto>

    @GET(NetworkConfig.Service.COMMENT_MY)
    suspend fun reqMyCommentList(
        @Query("language") language: String,
    ): Response<List<CommentContentDto>>

    // User
    @PUT(NetworkConfig.Service.USER_NICKNAME)
    suspend fun reqUserNickname(
        @Query("nickname") nickname: String,
    ): Response<UserNicknameDto>

    @GET(NetworkConfig.Service.USER_INFO)
    suspend fun reqUserInfo(): Response<UserInfoDto>

    @DELETE(NetworkConfig.Service.USER)
    suspend fun deleteUser(
        @Query("userId") userId: Int
    ): Response<DeleteUserDto>

}
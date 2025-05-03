package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeItemDetailDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "attractions") val attractions: List<AttractionItem>,
    @Json(name = "mainLocation") val mainLocation: String?,
    @Json(name = "challengeThemeId") val challengeThemeId: Int,
    @Json(name = "challengeThemeName") val challengeThemeName: String,
    @Json(name = "comments") val comments: List<ChallengeCommentItem>,
    @Json(name = "isLiked") val isLiked: Boolean?,
    @Json(name = "likedCount") val likedCount: Int,
    @Json(name = "progressCount") val progressCount: Int,
    @Json(name = "attractionCount") val attractionCount: Int,
    @Json(name = "commentCount") val commentCount: Int,
    @Json(name = "challengeStatusCode") val challengeStatusCode: String?,
)

@JsonClass(generateAdapter = true)
data class AttractionItem(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "locationX") val locationX: String?,
    @Json(name = "locationY") val locationY: String?,
    @Json(name = "isLiked") val isLiked: Boolean,
    @Json(name = "likes") val likes: Int,
    @Json(name = "isStamped") val isStamped: Boolean,
    @Json(name = "stampCount") val stampCount: Int,
    @Json(name = "address") val address: String?,
    @Json(name = "imageUrl") val imageUrl: String?,
)

@JsonClass(generateAdapter = true)
data class ChallengeCommentItem(
    @Json(name = "commentId") val commentId: Int,
    @Json(name = "comment") val comment: String,
    @Json(name = "createdAt") val createdAt: String,
//    @Json(name = "modifiedAt") val modifiedAt: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "isMine") val isMine: Boolean,
    @Json(name = "challengeStatusCode") val challengeStatusCode: String,
)

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: Int,
    @Json(name = "email") val email: String,
    @Json(name = "nicknameKor") val nicknameKor: String,
    @Json(name = "nicknameEng") val nicknameEng: String,
    @Json(name = "loginType") val loginType: String,
    @Json(name = "visitStamps") val visitStamps: VisitStamp,
)

package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeItemDetailDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "attractions") val attractions: List<AttractionItem>,
    @Json(name = "mainLocation") val mainLocation: String?,
    @Json(name = "challengeThemeId") val challengeThemeId: Int,
    @Json(name = "challengeThemeName") val challengeThemeName: String,
    @Json(name = "comments") val comments: List<ChallengeCommentItem>,
    @Json(name = "isLiked") val isLiked: Boolean?,
    @Json(name = "challengeStatusCode") val likes: String?,
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
)

@JsonClass(generateAdapter = true)
data class ChallengeCommentItem(
    @Json(name = "id") val id: Int,
    @Json(name = "comment") val comment: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "modifiedAt") val modifiedAt: String,
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

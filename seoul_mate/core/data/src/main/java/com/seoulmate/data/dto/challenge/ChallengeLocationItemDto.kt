package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeLocationItemDto(
    @Json(name = "challenges") val challenges: List<ChallengeLocationListItem> = listOf(),
    @Json(name = "jongGak") val jongGak: Boolean?,
    @Json(name = "code") val code: String? = null,
    @Json(name = "message") val message: String? = null,
)

@JsonClass(generateAdapter = true)
data class ChallengeLocationListItem(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "imageUrl") val imageUrl: String?,
    @Json(name = "mainLocation") val mainLocation: String?,
    @Json(name = "challengeThemeId") val challengeThemeId: Int?,
    @Json(name = "challengeThemeName") val challengeThemeName: String?,
    @Json(name = "isLiked") val isLiked: Boolean?,
    @Json(name = "likes") val likes: Int?,
    @Json(name = "myStampCount") val myStampCount: Int?,
    @Json(name = "attractionCount") val attractionCount: Int?,
    @Json(name = "commentCount") val commentCount: Int?,
    @Json(name = "distance") val distance: Int?,
    @Json(name = "displayRank") val displayRank: String?,
    @Json(name = "level") val level: String?,
)
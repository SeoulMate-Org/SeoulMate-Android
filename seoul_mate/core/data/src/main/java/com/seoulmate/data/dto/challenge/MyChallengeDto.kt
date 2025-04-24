package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyChallengeDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "like") val like: Int,
    @Json(name = "attractionCount") val attractionCount: Int,
    @Json(name = "myStampCount") val myStampCount: Int,
    @Json(name = "mainLocation") val mainLocation: String?,
    @Json(name = "challengeThemeId") val challengeThemeId: Int,
    @Json(name = "challengeThemeName") val challengeThemeName: String,
)

sealed class MyChallengeType(val type: String) {
    data object LIKE: MyChallengeType("LIKE")
    data object PROGRESS: MyChallengeType("PROGRESS")
    data object COMPLETE: MyChallengeType("COMPLETE")
}

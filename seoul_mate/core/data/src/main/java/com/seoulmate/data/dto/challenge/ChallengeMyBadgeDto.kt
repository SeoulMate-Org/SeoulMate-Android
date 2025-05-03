package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeMyBadgeDto(
    @Json(name = "themeId") val themeId: Int,
    @Json(name = "themeName") val themeName: String?,
    @Json(name = "themeCount") val themeCount: Int?,
    @Json(name = "completeCount") val completeCount: Int?,
    @Json(name = "isCompleted") val isCompleted: Boolean?,
)

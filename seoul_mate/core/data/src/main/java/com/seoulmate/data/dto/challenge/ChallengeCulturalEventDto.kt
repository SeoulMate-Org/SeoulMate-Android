package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeCulturalEventDto(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String?,
    @Json(name = "imageUrl") val imageUrl: String?,
    @Json(name = "startDate") val startDate: String?,
    @Json(name = "endDate") val endDate: String?,
    @Json(name = "isLiked") val isLiked: Boolean?,
)



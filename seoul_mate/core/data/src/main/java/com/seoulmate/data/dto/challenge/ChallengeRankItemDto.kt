package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeRankItemDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "title") val title: String,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "isLiked") val isLiked: Boolean?,
    @Json(name = "progressCount") val progressCount: Int,
)
package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeItemLikeDto(
    @Json(name = "id") val id: Int,
    @Json(name = "isLiked") val isLiked: Boolean,
)

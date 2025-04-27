package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeStatusDto(
    @Json(name = "id") val id: Int,
    @Json(name = "challengeStatusCode") val challengeStatusCode: String, // Available values : PROGRESS, COMPLETE
)

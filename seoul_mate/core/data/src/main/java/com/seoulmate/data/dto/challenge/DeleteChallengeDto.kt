package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteChallengeDto(
    @Json(name = "id") val id: Int?,
)
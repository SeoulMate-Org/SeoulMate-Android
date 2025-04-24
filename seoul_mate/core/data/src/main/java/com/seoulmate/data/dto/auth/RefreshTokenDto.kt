package com.seoulmate.data.dto.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenDto(
    @Json(name = "accessToken") val accessToken: String,
)

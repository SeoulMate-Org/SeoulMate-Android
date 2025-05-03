package com.seoulmate.data.dto.geocode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MapStaticDto(
    @Json(name = "status") val status: String?,
    @Json(name = "code") val code: String?,
)

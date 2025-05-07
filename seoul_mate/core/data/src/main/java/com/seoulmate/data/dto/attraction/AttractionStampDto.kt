package com.seoulmate.data.dto.attraction

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttractionStampDto(
    @Json(name = "id") val id: Int?,
    @Json(name = "isProcessed")val isProcessed: Boolean?,
)

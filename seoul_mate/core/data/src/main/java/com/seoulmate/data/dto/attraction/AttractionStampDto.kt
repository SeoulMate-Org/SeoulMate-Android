package com.seoulmate.data.dto.attraction

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttractionStampDto(
    val id: Int?,
    val isProcessed: Boolean?,
)

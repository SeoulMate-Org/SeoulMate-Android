package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeThemeDto(
    @Json(name = "id") val id: Int,
    @Json(name = "nameKor") val nameKor: String,
    @Json(name = "title") val title: String,
    @Json(name = "descriptionKor") val descriptionKor: String,
    @Json(name = "descriptionEng") val descriptionEng: String,
)
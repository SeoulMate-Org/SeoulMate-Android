package com.seoulmate.data.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserNicknameDto(
    @Json(name = "id") val id: Int,
    @Json(name = "nickname") val nickname: String,
)

package com.seoulmate.data.dto.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginDto(
    @Json(name = "id") val id: Int,
    @Json(name = "email") val email: String,
    @Json(name = "nickname") val nickName: String,
    @Json(name = "loginType") val loginType: String,
    @Json(name = "accessToken") val accessToken: String,
    @Json(name = "refreshToken") val refreshToken: String,
    @Json(name = "isNewUser") val isNewUser: Boolean,
)


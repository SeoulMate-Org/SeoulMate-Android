package com.seoulmate.data.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoDto(
    @Json(name = "id") val id: Int,
    @Json(name = "email") val email: String?,
    @Json(name = "nickname") val nickname: String?,
    @Json(name = "loginType") val loginType: String?,
    @Json(name = "likedCount") val likeCount: Int?,
    @Json(name = "badgeCount") val badgeCount: Int?,
    @Json(name = "commentCount") val commentCount: Int?,
    @Json(name = "code") val code: String?,
    @Json(name = "message") val message: String?,
)

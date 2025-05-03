package com.seoulmate.data.model.user

data class UserInfoData(
    val id: Int,
    val email: String,
    val nickname: String,
    val loginType: String,
    val likeCount: Int,
    val badgeCount: Int,
    val commentCount: Int,
)

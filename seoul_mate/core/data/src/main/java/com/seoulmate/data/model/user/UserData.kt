package com.seoulmate.data.model.user


data class UserData(
    val nickName: String,
    val accessToken: String,
    val refreshToken: String,
    val loginType: String,
)

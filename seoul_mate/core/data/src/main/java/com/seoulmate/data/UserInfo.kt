package com.seoulmate.data

object UserInfo {
    var nickName: String = ""
    var accessToken: String = ""
    var refreshToken: String = ""

    fun isUserLogin(): Boolean = accessToken.isNotEmpty()
}
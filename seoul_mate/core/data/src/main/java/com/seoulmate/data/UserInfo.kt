package com.seoulmate.data

object UserInfo {
    var nickName: String = ""
    var accessToken: String = ""
    var refreshToken: String = ""
    var loginType: String = ""

    fun isUserLogin(): Boolean = accessToken.isNotEmpty()
}
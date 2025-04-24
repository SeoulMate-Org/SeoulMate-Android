package com.seoulmate.data

object UserInfo {
    var nickName: String = ""
    var accessToken: String = ""
    var refreshToken: String = ""
    var loginType: String = ""
    var localeLanguage: String = ""

    fun isUserLogin(): Boolean = accessToken.isNotEmpty()
}
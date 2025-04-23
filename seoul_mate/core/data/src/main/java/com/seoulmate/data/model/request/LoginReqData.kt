package com.seoulmate.data.model.request

data class LoginReqData(
    val token: String,
    val loginType: String,
    val languageCode: String,
)

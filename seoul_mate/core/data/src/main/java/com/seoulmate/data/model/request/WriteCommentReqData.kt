package com.seoulmate.data.model.request

data class WriteCommentReqData(
    val challengeId: Int,
    val comment: String,
    val languageCode: String,
)

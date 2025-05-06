package com.seoulmate.data.model.request

data class ModifyCommentReqData(
    val commentId: Int,
    val comment: String,
    val languageCode: String,
)

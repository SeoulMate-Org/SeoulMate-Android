package com.seoulmate.data.model

data class ReplyItemData(
    val id: Int,
    val title: String,
    val userNickName: String,
    val timeStamp: String,
    val isMyReply: Boolean = false,
)

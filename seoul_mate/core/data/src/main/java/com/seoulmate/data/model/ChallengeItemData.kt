package com.seoulmate.data.model

data class ChallengeItemData(
    val id: Int,
    val title: String,
    val imgUrl: String,
    val themeList: List<String>,
    val isInterest: Boolean = false,
)

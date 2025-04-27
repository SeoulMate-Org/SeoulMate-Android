package com.seoulmate.data.model

data class MyChallengeItemData(
    val id: Int,
    val name: String,
    val title: String,
    val likes: Int,
    val attractionCount: Int,
    val myStampCount: Int,
    val mainLocation: String?,
    val challengeThemeId: Int,
    val challengeThemeName: String,
)

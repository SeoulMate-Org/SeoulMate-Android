package com.seoulmate.data.model.challenge

data class ChallengeThemeItemData(
    val id: Int,
    val name: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val mainLocation: String,
    val challengeThemeId: Int,
    val challengeThemeName: String,
    val likes: Int,
    var isLiked: Boolean,
    val myStampCount: Int,
    val attractionCount: Int,
    val commentCount: Int,
    val distance: Int,
    val displayRank: String,
)

package com.seoulmate.data.model

data class ChallengeLocationItemData(
    val id: Int,
    val name: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val mainLocation: String?,
    val challengeThemeId: Int,
    val challengeThemeName: String,
    val isLiked: Boolean?,
    val myStampCount: Int,
    val progressCount: Int,
    val attractionCount: Int,
    val commentCount: Int,
    val distance: Int,
)

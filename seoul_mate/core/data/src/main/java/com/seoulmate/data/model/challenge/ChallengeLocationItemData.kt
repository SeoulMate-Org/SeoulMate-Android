package com.seoulmate.data.model.challenge

data class ChallengeLocationItemData(
    val challenges: List<ChallengeLocationData> = listOf(),
    val jongGak: Boolean = false,
    val code: String? = null,
    val message: String? = null,
)

data class ChallengeLocationData(
    val id: Int,
    val name: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val mainLocation: String,
    val challengeThemeId: Int,
    val challengeThemeName: String,
    val isLiked: Boolean,
    val likes: Int,
    val myStampCount: Int,
    val attractionCount: Int,
    val commentCount: Int,
    val distance: Int,
    val displayRank: String,
    val level: String,
)

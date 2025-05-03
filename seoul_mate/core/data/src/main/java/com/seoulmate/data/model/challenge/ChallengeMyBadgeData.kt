package com.seoulmate.data.model.challenge


data class ChallengeMyBadgeData(
    val themeId: Int,
    val themeName: String,
    val themeCount: Int,
    val completeCount: Int,
    val isCompleted: Boolean,
)

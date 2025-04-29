package com.seoulmate.data.model.challenge

data class ChallengeRankItemData(
    val id: Int,
    val name: String,
    val title: String,
    val imageUrl: String,
    val isLiked: Boolean?,
    val progressCount: Int,
)

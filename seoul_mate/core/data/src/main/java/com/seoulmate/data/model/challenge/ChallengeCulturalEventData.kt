package com.seoulmate.data.model.challenge

data class ChallengeCulturalEventData(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val startDate: String,
    val endDate: String,
    val isLiked: Boolean,
)

package com.seoulmate.data.dto.challenge

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeItemLikeDto(
    val status: String,
)

package com.seoulmate.data.model.attraction

data class AttractionDetailData(
    val id: Int,
    val name: String,
    val description: String,
    val detailCodes: List<String>,
    val address: String,
    val businessHours: String,
    val homepageUrl: String,
    val locationX: String,
    val locationY: String,
    val tel: String,
    val subway: String,
    val imageUrl: String,
    val likes: Int,
    val isLiked: Boolean,
    val stampCount: Int,
)

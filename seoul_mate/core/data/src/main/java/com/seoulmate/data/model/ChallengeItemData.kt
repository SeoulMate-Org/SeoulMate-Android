package com.seoulmate.data.model

data class ChallengeItemData(
    val id: Int,
    val title: String,
    val titleEng: String = "",
    val description: String = "",
    val descriptionEng: String = "",
    val imgUrl: String,
    val themeList: List<Int> = listOf(),
    val attractionIdList: List<Int> = listOf(),
    val attractions: List<AttractionItem> = listOf(),
    val isInterest: Boolean = false,
    val mainLocation: String? = "",
)

data class AttractionItem(
    val id: Int,
    val name: String,
    val locationX: String,
    val locationY: String,
    val isLiked: Boolean,
    val likes: Int,
    val isStamped: Boolean,
    val stampCount: Int,
)

object DefaultChallengeItemData {
    val item = ChallengeItemData(
        id = 0,
        title = "",
        imgUrl = "",
    )
}
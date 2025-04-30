package com.seoulmate.data.model.challenge


data class ChallengeItemData(
    val id: Int,
    val title: String,
    val name: String,
    val description: String = "",
    val imgUrl: String,
    val themeList: List<Int> = listOf(),
    val attractionIdList: List<Int> = listOf(),
    val attractions: List<AttractionItem> = listOf(),
    val isInterest: Boolean = false,
    val mainLocation: String? = "",
    val likedCount: Int = 0,
    val progressCount: Int = 0,
    val attractionCount: Int = 0,
    val commentCount: Int = 0,
    val comments: List<ChallengeCommentItem> = listOf(),
)

data class AttractionItem(
    val id: Int,
    val name: String?,
    val locationX: String?,
    val locationY: String?,
    val isLiked: Boolean,
    val likes: Int,
    val isStamped: Boolean,
    val stampCount: Int,
    val address: String?,
)

data class ChallengeCommentItem(
    val id: Int,
    val comment: String,
    val createdAt: String,
    val modifiedAt: String = "",
    val isMine: Boolean = false,
    val nickname: String = ""
)

object DefaultChallengeItemData {
    val item = ChallengeItemData(
        id = 0,
        title = "",
        name = "",
        imgUrl = "",
    )
}
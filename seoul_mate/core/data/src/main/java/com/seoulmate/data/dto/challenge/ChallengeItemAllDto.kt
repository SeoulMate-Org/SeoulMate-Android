package com.seoulmate.data.dto.challenge

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChallengeItemAllDto(
    @Json(name = "totalPages") val totalPages: Int,
    @Json(name = "totalElements") val totalElements: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "content") val content: List<ChallengeContentItem>,
    @Json(name = "number") val number: Int,
    @Json(name = "numberOfElements") val numberOfElements: Int,
    @Json(name = "first") val first: Boolean,
    @Json(name = "last") val last: Boolean,
)

@JsonClass(generateAdapter = true)
data class ChallengeContentItem(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "nameEng") val nameEng: String,
    @Json(name = "title") val title: String,
    @Json(name = "titleEng") val titleEng: String,
    @Json(name = "description") val description: String,
    @Json(name = "descriptionEng") val descriptionEng: String,
    @Json(name = "attractionIdList") val attractionIdList: List<Int>,
    @Json(name = "mainLocation") val mainLocation: String?,
    @Json(name = "challengeThemeId") val challengeThemeId: Int,
    @Json(name = "comments") val comments: List<ChallengeCommentItem>,

)



@JsonClass(generateAdapter = true)
data class VisitStamp(
    @Json(name = "id") val id: Int,
    @Json(name = "user") val user: String,
    @Json(name = "attraction") val attraction: String,
    @Json(name = "createdAt") val createdAt: String,
)

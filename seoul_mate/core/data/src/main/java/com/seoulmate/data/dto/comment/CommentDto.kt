package com.seoulmate.data.dto.comment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentDto(
    @Json(name = "totalPages") val totalPages: Int,
    @Json(name = "totalElements") val totalElements: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "content") val content: List<CommentContent>,
    @Json(name = "first") val first: Boolean,
    @Json(name = "last") val last: Boolean,
)

@JsonClass(generateAdapter = true)
data class WriteCommentDto(
    @Json(name = "commentId") val commentId: Int,
    @Json(name = "comment") val comment: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "isMine") val isMine: Boolean,
    @Json(name = "challengeId") val challengeId: Int,
    @Json(name = "createdAt") val createdAt: String,
)


@JsonClass(generateAdapter = true)
data class CommentContent(
    @Json(name = "commentId") val commentId: Int,
    @Json(name = "comment") val comment: String,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "isMine") val isMine: Boolean,
    @Json(name = "challengeId") val challengeId: Int,
    @Json(name = "createdAt") val createdAt: String,
)

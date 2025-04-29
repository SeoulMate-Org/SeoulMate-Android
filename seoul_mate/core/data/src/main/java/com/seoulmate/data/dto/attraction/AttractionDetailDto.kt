package com.seoulmate.data.dto.attraction

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttractionDetailDto(
    @Json(name = "id")  val id: Int,
    @Json(name = "name")  val name: String,
    @Json(name = "description")  val description: String,
    @Json(name = "detailCodes")  val detailCodes: List<String>,
    @Json(name = "address")  val address: String,
    @Json(name = "businessHours")  val businessHours: String,
    @Json(name = "homepageUrl")  val homepageUrl: String,
    @Json(name = "locationX")  val locationX: String,
    @Json(name = "locationY")  val locationY: String,
    @Json(name = "tel")  val tel: String,
    @Json(name = "subway")  val subway: String,
    @Json(name = "imageUrl")  val imageUrl: String,
    @Json(name = "likes")  val likes: Int,
    @Json(name = "isLiked")  val isLiked: Boolean,
    @Json(name = "stampCount")  val stampCount: Int,
)



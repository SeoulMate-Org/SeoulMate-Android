package com.seoulmate.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeocodeDTO (
    @Json(name = "status") val status: String,
    @Json(name = "meta") val meta: GeocodeMeta,
    @Json(name= "addresses") val addresses: List<GeocodeAddress>,
    @Json(name = "errorMessage") val errorMessage: String?,
)

@JsonClass(generateAdapter = true)
data class GeocodeAddress (
    @Json(name = "roadAddress") val roadAddress: String,
    @Json(name = "jibunAddress") val jibunAddress: String,
    @Json(name = "x") val x: String,
    @Json(name = "y") val y: String,
)

@JsonClass(generateAdapter = true)
data class GeocodeMeta (
    @Json(name = "totalCount") val totalCount: Int,
    @Json(name = "page") val page: Int,
    @Json(name = "count") val count: Int,
)
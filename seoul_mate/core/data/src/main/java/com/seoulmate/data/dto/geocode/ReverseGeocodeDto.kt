package com.seoulmate.data.dto.geocode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReverseGeocodeDto(
    @Json(name = "status") val status: ReverseGeocodeStatus,
    @Json(name = "results") val results: List<ReverseGeocodeResult>,
)

@JsonClass(generateAdapter = true)
data class ReverseGeocodeStatus(
    @Json(name = "code") val code: Int,
    @Json(name = "name") val name: String,
    @Json(name = "message") val message: String,
)

@JsonClass(generateAdapter = true)
data class ReverseGeocodeResult(
    @Json(name = "name") val name: String,
    @Json(name = "region") val region: ReverseGeocodeRegion,

)

@JsonClass(generateAdapter = true)
data class ReverseGeocodeRegion(
    @Json(name = "area0") val area0: String,
    @Json(name = "area1") val area1: String,
    @Json(name = "area2") val area2: String,
    @Json(name = "area3") val area3: String,
    @Json(name = "area4") val area4: String,
)

@JsonClass(generateAdapter = true)
data class ReverseGeocodeAddress(
    @Json(name = "name") val name: String,
)



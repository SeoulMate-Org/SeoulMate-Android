package com.seoulmate.data.api

import com.google.gson.JsonObject
import com.seoulmate.data.dto.geocode.GeocodeDTO
import com.seoulmate.data.dto.geocode.MapStaticDto
import com.seoulmate.data.dto.geocode.ReverseGeocodeDto
import com.seoulmate.data.utils.NetworkConfig
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverMapApiService {

    @GET(NetworkConfig.NaverMapService.GEOCODE)
    suspend fun reqNaverMapGeocode(
        @Query("query") query: String,
    ): Response<GeocodeDTO>

    @GET(NetworkConfig.NaverMapService.REVERSE_GEOCODE)
    suspend fun reqNaverMapReverseGeocode(
        @Query("coords") coords: String,
        @Query("output") output: String = "json",
        @Query("orders") orders: String = "roadaddr",
    ): Response<ReverseGeocodeDto>

    @GET(NetworkConfig.NaverMapService.MAP_STATIC)
    suspend fun reqNaverMapStatic(
        @Query("w") w: Int = 670,
        @Query("h") h: Int = 400,
        @Query("markers") markers: String,
    ): Response<JSONObject>
}
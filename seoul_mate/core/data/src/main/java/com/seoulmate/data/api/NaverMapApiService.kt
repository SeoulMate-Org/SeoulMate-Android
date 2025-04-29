package com.seoulmate.data.api

import com.seoulmate.data.dto.geocode.GeocodeDTO
import com.seoulmate.data.dto.geocode.ReverseGeocodeDto
import com.seoulmate.data.utils.NetworkConfig
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
}
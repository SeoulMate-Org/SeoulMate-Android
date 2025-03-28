package com.seoulmate.data.api

import com.seoulmate.data.dto.GeocodeDTO
import com.seoulmate.data.utils.NetworkConfig
import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverMapApiService {

    @GET(NetworkConfig.NaverMapService.GEOCODE)
    suspend fun reqNaverMapGeocode(
        @Query("query") query: String,
    ): Response<GeocodeDTO>
}
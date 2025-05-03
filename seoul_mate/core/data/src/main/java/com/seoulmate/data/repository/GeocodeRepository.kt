package com.seoulmate.data.repository

import com.google.gson.JsonObject
import com.seoulmate.data.dto.geocode.GeocodeDTO
import com.seoulmate.data.dto.geocode.MapStaticDto
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface GeocodeRepository {
    suspend fun getAddresses(query: String): Flow<GeocodeDTO?>

    suspend fun getAddressesToReverseGeocode(query: String): Flow<GeocodeDTO?>

    suspend fun getMapStatic(x: String, y: String): Flow<JSONObject?>
}
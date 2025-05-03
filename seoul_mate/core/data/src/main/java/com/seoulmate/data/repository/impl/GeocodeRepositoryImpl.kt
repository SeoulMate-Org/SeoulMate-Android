package com.seoulmate.data.repository.impl

import com.google.gson.JsonObject
import com.seoulmate.data.api.NaverMapApiService
import com.seoulmate.data.dto.geocode.GeocodeDTO
import com.seoulmate.data.dto.geocode.MapStaticDto
import com.seoulmate.data.repository.GeocodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject

class GeocodeRepositoryImpl @Inject constructor(
    private val naverMapApiService: NaverMapApiService
): GeocodeRepository {
    override suspend fun getAddresses(query: String): Flow<GeocodeDTO?> = flow {
        val respone = naverMapApiService.reqNaverMapGeocode(query = query)
        emit(respone.body())
    }

    override suspend fun getAddressesToReverseGeocode(query: String): Flow<GeocodeDTO?> {
        TODO("Not yet implemented")
    }

    override suspend fun getMapStatic(x: String, y: String): Flow<JSONObject?> = flow {
        val markers = "type:d|size:mid|color:red|pos:$x $y"
        val response = naverMapApiService.reqNaverMapStatic(markers = markers)
        emit(response.body())
    }
}
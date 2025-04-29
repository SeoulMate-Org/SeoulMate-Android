package com.seoulmate.data.repository

import com.seoulmate.data.dto.geocode.GeocodeDTO
import kotlinx.coroutines.flow.Flow

interface GeocodeRepository {
    suspend fun getAddresses(query: String): Flow<GeocodeDTO?>

    suspend fun getAddressesToReverseGeocode(query: String): Flow<GeocodeDTO?>
}
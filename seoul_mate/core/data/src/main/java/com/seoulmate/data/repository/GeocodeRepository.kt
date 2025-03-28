package com.seoulmate.data.repository

import com.seoulmate.data.dto.GeocodeDTO
import kotlinx.coroutines.flow.Flow

interface GeocodeRepository {
    suspend fun getAddresses(query: String): Flow<GeocodeDTO?>
}
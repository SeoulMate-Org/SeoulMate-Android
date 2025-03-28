package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.GeocodeDTO
import com.seoulmate.data.repository.GeocodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressesFromGeocodeUseCase @Inject constructor(
    private val geocodeRepository: GeocodeRepository
) {

    suspend fun getAddresses(query: String): Flow<GeocodeDTO?> = geocodeRepository.getAddresses(query = query)
}
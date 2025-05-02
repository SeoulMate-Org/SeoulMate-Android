package com.seoulmate.data.repository.impl

import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.datastore.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferDataStoreRepositoryImpl @Inject constructor(
    private val dataSource: DataStoreRepository
): PreferDataStoreRepository {

    override suspend fun loadLanguage(): Flow<String> = dataSource.getLanguage()
    override suspend fun updateLanguage(language: String): String =
        dataSource.setLanguage(language)

    override suspend fun loadIsFirstEnter(): Flow<Boolean> = dataSource.getIsFirstEnter()
    override suspend fun updateIsFirstEnter(isFirstEnter: Boolean): String =
        dataSource.setIsFirstEnter(isFirstEnter)

    override suspend fun loadLastStampedAttractionId(): Flow<Int> = dataSource.getLastStampedAttractionId()
    override suspend fun updateLastStampedAttractionId(lastStampedAttractionId: Int): String =
        dataSource.setLastStampedAttractionId(lastStampedAttractionId)

}
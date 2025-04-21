package com.seoulmate.data.repository

import kotlinx.coroutines.flow.Flow

interface PreferDataStoreRepository {

    suspend fun loadLanguage(): Flow<String>
    suspend fun updateLanguage(language: String): String

    suspend fun loadIsFirstEnter(): Flow<Boolean>
    suspend fun updateIsFirstEnter(isFirstEnter: Boolean): String
}
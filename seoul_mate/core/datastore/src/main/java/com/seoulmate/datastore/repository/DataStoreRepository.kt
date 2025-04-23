package com.seoulmate.datastore.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

enum class Language(val code: String) {
    ENGLISH("en"),
    KOREAN("ko")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pref_data")


class DataStoreRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
    object DataStoreResult {
        const val SET_SUCCESS = "Success"
    }

    // Key 설정
    private val notifyIdKey = intPreferencesKey("notifyId")
    private val languageKey = stringPreferencesKey("language")
    private val isFirstEnterKey = booleanPreferencesKey("isFirstEnter")
    private val accessTokenKey = stringPreferencesKey("accessToken")
    private val refreshTokenKey = stringPreferencesKey("refreshToken")
    private val nicknameKey = stringPreferencesKey("nickname")

    // 쓰기
    suspend fun setNotifyId(): String {
        context.dataStore.edit {
            getNotifyId().collectLatest { id ->
                it[notifyIdKey] = id + 1
            }
        }
        return DataStoreResult.SET_SUCCESS
    }

    suspend fun setLanguage(language: String): String {
        context.dataStore.edit {
            it[languageKey] = language
        }
        return DataStoreResult.SET_SUCCESS
    }

    suspend fun setIsFirstEnter(isFirstEnter: Boolean): String {
        context.dataStore.edit {
            it[isFirstEnterKey] = isFirstEnter
        }
        return DataStoreResult.SET_SUCCESS
    }

    suspend fun setAccessToken(accessToken: String): String {
        context.dataStore.edit {
            it[accessTokenKey] = accessToken
        }
        return DataStoreResult.SET_SUCCESS
    }

    suspend fun setRefreshToken(refreshToken: String): String {
        context.dataStore.edit {
            it[refreshTokenKey] = refreshToken
        }
        return DataStoreResult.SET_SUCCESS
    }

    suspend fun setNickname(nickname: String): String {
        context.dataStore.edit {
            it[nicknameKey] = nickname
        }
        return DataStoreResult.SET_SUCCESS
    }


    // 읽기
    fun getNotifyId(): Flow<Int> {
        return context.dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[notifyIdKey] ?: 0
        }
    }

    fun getLanguage(): Flow<String> {
        return context.dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[languageKey] ?: ""
        }
    }

    fun getIsFirstEnter(): Flow<Boolean> {
        return context.dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[isFirstEnterKey] ?: true
        }
    }

    fun getAccessToken(): Flow<String> {
        return context.dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[accessTokenKey] ?: ""
        }
    }

    fun getRefreshToken(): Flow<String> {
        return context.dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[refreshTokenKey] ?: ""
        }
    }

    fun getNickName(): Flow<String> {
        return context.dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[nicknameKey] ?: ""
        }
    }
}
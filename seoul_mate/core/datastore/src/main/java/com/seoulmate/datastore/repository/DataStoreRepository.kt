package com.seoulmate.datastore.repository

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.seoulmate.datastore.di.DataStoreModule.dataStore
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class DataStoreRepository @Inject constructor(
    private val context: Context
) {
    object DataStoreResult {
        const val SET_NOTIFY_ID = "Set NotifyId Success"
    }

    // Key 설정
    private val notifyIdKey = intPreferencesKey("notifyId")

    // 쓰기
    suspend fun setNotifyId(): String {
        context.dataStore.edit {
            getNotifyId().collectLatest { id ->
                it[notifyIdKey] = id + 1
            }
        }
        return DataStoreResult.SET_NOTIFY_ID
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
}
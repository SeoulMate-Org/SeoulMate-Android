package com.seoulmate.data.repository

import com.seoulmate.data.dto.LoginDto
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun getLoginInfo(
        token: String,
        loginType: String,
        languageCode: String,
    ): Flow<LoginDto?>
}
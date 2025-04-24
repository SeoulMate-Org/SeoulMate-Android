package com.seoulmate.data.repository

import com.seoulmate.data.dto.auth.LoginDto
import com.seoulmate.data.dto.auth.RefreshTokenDto
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getLoginInfo(
        token: String,
        loginType: String,
        languageCode: String,
    ): Flow<LoginDto?>

    suspend fun getToken(
        refreshToken: String,
    ): Flow<RefreshTokenDto?>
}
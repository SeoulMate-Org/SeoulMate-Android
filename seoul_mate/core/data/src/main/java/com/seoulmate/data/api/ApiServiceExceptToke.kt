package com.seoulmate.data.api

import com.seoulmate.data.dto.auth.LoginDto
import com.seoulmate.data.dto.auth.RefreshTokenDto
import com.seoulmate.data.model.request.LoginReqData
import com.seoulmate.data.model.request.TokenReqData
import com.seoulmate.data.utils.NetworkConfig
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServiceExceptToke {
    // Auth
    @POST(NetworkConfig.Service.LOGIN)
    suspend fun reqLogin(
        @Body body: LoginReqData,
    ): Response<LoginDto>

    @POST(NetworkConfig.Service.REFRESH)
    suspend fun reqRefreshToken(
        @Body body: TokenReqData,
    ): Response<RefreshTokenDto>

    @POST(NetworkConfig.Service.REFRESH)
    suspend fun reqRefreshTokenTest(
        @Query("refreshToken") refreshToken: String,
    ): Response<RefreshTokenDto>
}
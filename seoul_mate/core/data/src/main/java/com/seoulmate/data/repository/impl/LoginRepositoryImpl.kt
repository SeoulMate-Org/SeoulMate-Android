package com.seoulmate.data.repository.impl

import com.seoulmate.data.api.ApiServiceExceptToke
import com.seoulmate.data.dto.auth.LoginDto
import com.seoulmate.data.dto.auth.RefreshTokenDto
import com.seoulmate.data.model.request.LoginReqData
import com.seoulmate.data.model.request.TokenReqData
import com.seoulmate.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiServiceExceptToke,
): LoginRepository {

    override suspend fun getLoginInfo(
        token: String,
        loginType: String,
        languageCode: String
    ): Flow<LoginDto?> = flow {
        val response = apiService.reqLogin(
            LoginReqData(
                token = token,
                loginType = loginType,
                languageCode = languageCode,
            )
        )
        emit(response.body())
    }

    override suspend fun getToken(refreshToken: String): Flow<RefreshTokenDto?> = flow {
//        val response = apiService.reqRefreshToken(
//            TokenReqData(refreshToken)
//        )
        val response = apiService.reqRefreshTokenTest(
            refreshToken
        )
        emit(response.body())
    }

}
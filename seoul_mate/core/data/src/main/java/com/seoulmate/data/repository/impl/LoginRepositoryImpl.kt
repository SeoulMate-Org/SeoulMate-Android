package com.seoulmate.data.repository.impl

import com.seoulmate.data.api.ApiService
import com.seoulmate.data.dto.LoginDto
import com.seoulmate.data.model.request.LoginReqData
import com.seoulmate.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
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

}
package com.seoulmate.data.api

import com.seoulmate.data.dto.LoginDto
import com.seoulmate.data.model.request.LoginReqData
import com.seoulmate.data.utils.NetworkConfig
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(NetworkConfig.Service.LOGIN)
    suspend fun reqLogin(
        @Body body: LoginReqData,
    ): Response<LoginDto>

}
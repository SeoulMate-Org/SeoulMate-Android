package com.seoulmate.data.api

import com.seoulmate.data.dto.challenge.ChallengeItemAllDto
import com.seoulmate.data.dto.challenge.ChallengeItemDetailDto
import com.seoulmate.data.dto.challenge.ChallengeThemeDto
import com.seoulmate.data.dto.challenge.MyChallengeDto
import com.seoulmate.data.utils.NetworkConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Challenge
    @GET(NetworkConfig.Service.CHALLENGE)
    suspend fun reqChallengeItemAll(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("keyword") keyword: String,
    ): Response<ChallengeItemAllDto>

    @GET(NetworkConfig.Service.CHALLENGE + "/{id}")
    suspend fun reqChallengeItemDetail(
        @Path("id") id: Int,
        @Query("language") language: String,
    ): Response<ChallengeItemDetailDto>

    @GET(NetworkConfig.Service.CHALLENGE_THEME)
    suspend fun reqChallengeTheme(): Response<List<ChallengeThemeDto>>

    @GET(NetworkConfig.Service.CHALLENGE_MY)
    suspend fun reqMyChallenge(
        @Query("myChallenge") myChallenge: String, // Available values : LIKE, PROGRESS, COMPLETE
        @Query("language") language: String,
    ): Response<List<MyChallengeDto>>



}
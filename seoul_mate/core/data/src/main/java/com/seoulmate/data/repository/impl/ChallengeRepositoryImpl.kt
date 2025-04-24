package com.seoulmate.data.repository.impl

import com.seoulmate.data.api.ApiService
import com.seoulmate.data.dto.challenge.ChallengeItemAllDto
import com.seoulmate.data.dto.challenge.ChallengeItemDetailDto
import com.seoulmate.data.dto.challenge.ChallengeThemeDto
import com.seoulmate.data.dto.challenge.MyChallengeDto
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
): ChallengeRepository {

    override suspend fun getChallengeItemAll(
        page: Int,
        size: Int,
        keyword: String,
    ): Flow<ChallengeItemAllDto?> = flow {
        val response = apiService.reqChallengeItemAll(
            page = page,
            size = size,
            keyword = keyword,
        )
        emit(response.body())
    }

    override suspend fun getChallengeItemDetail(
        id: Int,
        language: String
    ): Flow<ChallengeItemDetailDto?> = flow {
        val response = apiService.reqChallengeItemDetail(
            id = id,
            language = language,
        )
        emit(response.body())
    }

    override suspend fun getChallengeTheme(): Flow<List<ChallengeThemeDto>?> = flow {
        val response = apiService.reqChallengeTheme()
        emit(response.body())
    }

    override suspend fun getMyChallengeList(
        type: String,
        language: String,
    ): Flow<List<MyChallengeDto>?> = flow {
        val response = apiService.reqMyChallenge(
            myChallenge = type,
            language = language,
        )
        emit(response.body())
    }


}
package com.seoulmate.data.repository

import com.seoulmate.data.dto.challenge.ChallengeItemAllDto
import com.seoulmate.data.dto.challenge.ChallengeItemDetailDto
import com.seoulmate.data.dto.challenge.ChallengeThemeDto
import com.seoulmate.data.dto.challenge.MyChallengeDto
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    // fetch challenge item all
    suspend fun getChallengeItemAll(
        page: Int,
        size: Int,
        keyword: String = "",
    ): Flow<ChallengeItemAllDto?>

    // fetch challenge item detail
    suspend fun getChallengeItemDetail(
        id: Int,
        language: String = "KOR",
    ): Flow<ChallengeItemDetailDto?>

    // fetch challenge theme item list
    suspend fun getChallengeTheme(): Flow<List<ChallengeThemeDto>?>

    // fetch my challenge item list
    suspend fun getMyChallengeList(
        type: String,
        language: String = "KOR",
    ): Flow<List<MyChallengeDto>?>
}
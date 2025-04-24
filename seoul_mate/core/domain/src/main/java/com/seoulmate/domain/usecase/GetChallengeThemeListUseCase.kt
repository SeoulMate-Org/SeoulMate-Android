package com.seoulmate.domain.usecase

import com.seoulmate.data.model.ChallengeThemeData
import com.seoulmate.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChallengeThemeListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
) {

    suspend operator fun invoke(): Flow<List<ChallengeThemeData>> =
        challengeRepository.getChallengeTheme().map { response ->
            val challengeThemeList = mutableListOf<ChallengeThemeData>()
            response?.let {
                it.forEach { data ->
                    challengeThemeList.add(
                        ChallengeThemeData(
                            id = data.id,
                            nameKor = data.nameKor,
                            title = data.title,
                            descriptionKor = data.descriptionKor,
                            descriptionEng = data.descriptionEng,
                        )
                    )
                }
            }
            return@map challengeThemeList.toList()
        }

}
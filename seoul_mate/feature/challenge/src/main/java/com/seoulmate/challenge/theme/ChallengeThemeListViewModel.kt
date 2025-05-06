package com.seoulmate.challenge.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.domain.usecase.ReqChallengeLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeThemeListViewModel @Inject constructor(
    private val reqChallengeLikeUseCase: ReqChallengeLikeUseCase,
) : ViewModel() {

    // Challenge Like or UnLike
    fun reqChallengeLike(
        challengeId: Int,
    ) {
        viewModelScope.launch {
            reqChallengeLikeUseCase(
                id = challengeId,
            ).collectLatest { response ->
                if (response.code in 200..299) {
                    response.response?.let {
                        ChallengeInfo.challengeCulturalList =
                            ChallengeInfo.challengeCulturalList.map { item ->
                                if (item.id == challengeId) {
                                    item.copy(
                                        isLiked = it.isLiked
                                    )
                                } else {
                                    item
                                }
                            }
                        ChallengeInfo.setChallengeStampList(
                            ChallengeInfo.getChallengeStampList().map { item ->
                                if (item.id == challengeId) {
                                    item.copy(
                                        isLiked = it.isLiked
                                    )
                                } else {
                                    item
                                }
                            }
                        )
                        ChallengeInfo.rankList = ChallengeInfo.rankList.map { item ->
                            if (item.id == challengeId) {
                                item.copy(
                                    isLiked = it.isLiked
                                )
                            } else {
                                item
                            }
                        }
                        ChallengeInfo.challengeThemeList =
                            ChallengeInfo.challengeThemeList.map { item ->
                                item.map { challengeItem ->
                                    if (challengeItem.id == challengeId) {
                                        challengeItem.copy(
                                            isLiked = it.isLiked
                                        )
                                    } else {
                                        challengeItem
                                    }

                                }
                            }
                    }
                }
            }
        }
    }
}
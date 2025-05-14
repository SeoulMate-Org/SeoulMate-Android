package com.seoulmate.challenge.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.ReqChallengeLikeUseCase
import com.seoulmate.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeThemeListViewModel @Inject constructor(
    private val reqChallengeLikeUseCase: ReqChallengeLikeUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
) : ViewModel() {

    var needUserLogin = mutableStateOf(false)
    var needRefreshToken = mutableStateOf<Boolean?>(null)
    var selectedChallengeId = mutableStateOf<Int?>(null)

    // Challenge Like or UnLike
    fun reqChallengeLike(
        challengeId: Int,
    ) {
        selectedChallengeId.value = challengeId

        viewModelScope.launch {
            reqChallengeLikeUseCase(
                id = challengeId,
            ).collectLatest { response ->
                if (response.code in 200..299) {
                    selectedChallengeId.value =  null
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
                } else if (response.code == 403) {
                    if (UserInfo.isUserLogin()) {
                        // Need Refresh Token
                        needRefreshToken.value = true
                    } else {
                        // Need Login
                        needUserLogin.value = true
                    }
                }
            }
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            refreshTokenUseCase(UserInfo.refreshToken).collectLatest { response ->
                response?.let {
                    selectedChallengeId.value = null
                    UserInfo.refreshToken = it.refreshToken
                    UserInfo.accessToken = it.accessToken

                    updateUserInfoUseCase(
                        userId = UserInfo.id,
                        nickName = UserInfo.nickName,
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        loginType = UserInfo.loginType,
                    )

                    needRefreshToken.value = false
                }
            }
        }
    }

}
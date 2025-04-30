package com.seoulmate.challenge.attraction

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.attraction.AttractionDetailData
import com.seoulmate.domain.usecase.GetAttractionDetailUseCase
import com.seoulmate.domain.usecase.ReqAttractionLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttractionDetailViewModel @Inject constructor(
    private val getAttractionDetailUseCase: GetAttractionDetailUseCase,
    private val reqAttractionLikeUseCase: ReqAttractionLikeUseCase,
): ViewModel() {

    val languageCode = if(UserInfo.localeLanguage == "ko") "KOR" else "ENG"
    var isShowLoading = mutableStateOf(false)
    var attractionItem = mutableStateOf<AttractionDetailData?>(null)
    var needUserLogin = mutableStateOf(false)

    fun getAttractionDetailInfo(
        attractionId: Int,
    ) {
        viewModelScope.launch {
            isShowLoading.value = true
            getAttractionDetailUseCase(
                attractionId, languageCode
            ).collectLatest { response ->
                isShowLoading.value = false
                if (response.code in 200..299) {
                    attractionItem.value = response.response
                } else if (response.code == 403) {
                    if (UserInfo.isUserLogin()) {
                        // Need Refresh Token
                    } else {
                        // Need Login
                        needUserLogin.value = true
                    }
                }
            }
        }
    }

    // Attraction Like or UnLike
    fun reqAttractionLike(
        attractionId: Int
    ) {
        viewModelScope.launch {
            reqAttractionLikeUseCase(attractionId)
                .collectLatest { response ->
                    if (response.code in 200..299) {
                        response.response?.let {
                            attractionItem.value?.let {
                                attractionItem.value = it.copy(
                                    isLiked = it.isLiked
                                )
                            }
                            ChallengeDetailInfo.attractions = ChallengeDetailInfo.attractions.map {
                                if (it.id == attractionId) {
                                    it.copy(
                                        isLiked = it.isLiked
                                    )
                                } else {
                                    it
                                }
                            }

                        }
                    } else if (response.code == 403) {
                        if (UserInfo.isUserLogin()) {
                            // Refresh Token
                        } else {
                            // Need Login
                            needUserLogin.value = true
                        }
                    }
                }
        }
    }

}
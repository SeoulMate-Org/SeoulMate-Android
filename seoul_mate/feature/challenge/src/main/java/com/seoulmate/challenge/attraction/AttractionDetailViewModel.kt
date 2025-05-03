package com.seoulmate.challenge.attraction

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.attraction.AttractionDetailData
import com.seoulmate.data.repository.GeocodeRepository
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
    private val geocodeRepository: GeocodeRepository,
): ViewModel() {

    var isShowLoading = mutableStateOf(false)
    var attractionItem = mutableStateOf<AttractionDetailData?>(null)
    var needUserLogin = mutableStateOf(false)

    fun getAttractionDetailInfo(
        attractionId: Int,
    ) {
        viewModelScope.launch {
            isShowLoading.value = true
            getAttractionDetailUseCase(
                attractionId, UserInfo.getLanguageCode()
            ).collectLatest { response ->
                isShowLoading.value = false
                if (response.code in 200..299) {
                    attractionItem.value = response.response
//                    test()
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

    fun test() {
        viewModelScope.launch {
            attractionItem.value?.let {
                geocodeRepository.getMapStatic(
                    x = it.locationX,
                    y = it.locationY
                ).collectLatest { item ->
                    Log.d("@@@@@@@", "item")
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
package com.seoulmate.challenge.attraction

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.attraction.AttractionDetailData
import com.seoulmate.domain.usecase.GetAttractionDetailUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AttractionDetailViewModel @Inject constructor(
    private val getAttractionDetailUseCase: GetAttractionDetailUseCase,
): ViewModel() {

    val languageCode = if(UserInfo.localeLanguage == "ko") "KOR" else "ENG"
    var isShowLoading = mutableStateOf(false)
    var attractionItem = mutableStateOf<AttractionDetailData?>(null)

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
                    }
                }
            }
        }
    }

}
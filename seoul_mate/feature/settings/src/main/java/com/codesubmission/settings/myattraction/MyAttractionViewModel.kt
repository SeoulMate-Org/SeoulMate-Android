package com.codesubmission.settings.myattraction

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.attraction.AttractionDetailData
import com.seoulmate.domain.usecase.GetMyAttractionListUseCase
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAttractionViewModel @Inject constructor(
    private val getMyAttractionListUseCase: GetMyAttractionListUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
): ViewModel() {

    var isShowLoading = mutableStateOf(false)
    var myAttractionList = mutableStateOf<List<AttractionDetailData>>(listOf())
    var needRefreshToken = mutableStateOf<Boolean?>(null)

    fun reqMyAttractionList() {
        viewModelScope.launch {
            isShowLoading.value = true
            getMyAttractionListUseCase(UserInfo.getLanguageCode()).collectLatest { response ->
                if (response.code in 200..299) {
                    response.response?.let {
                        myAttractionList.value = it
                    }
                } else if (response.code == 403) {
                    needRefreshToken.value = true
                }
            }
            isShowLoading.value = false
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            refreshTokenUseCase(UserInfo.refreshToken).collectLatest { response ->
                response?.let {
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